package br.imaginefree.city.bg

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.imaginefree.city.R
import br.imaginefree.city.download
import br.imaginefree.city.downloadImages
import br.imaginefree.city.model.City
import br.imaginefree.city.unzipWorker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.ZipFile

class UnzipWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams)  {

    override fun doWork(): Result {
        val zipFileName = File(applicationContext.filesDir, "images.zip")

        ZipFile(zipFileName).use { zip ->
            zip.entries().asSequence().forEach { entry ->
                if (!entry.name.startsWith("__MACOSX/")) {
                    zip.getInputStream(entry).use { input ->
                        File(applicationContext.filesDir, entry.name).outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            }
        }

        return Result.success(Data.Builder().putString("zip_path", "/unzip").build())
    }
}

class DownloadWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private var urlConnection: HttpURLConnection? = null

    override fun doWork(): Result {

        val url = URL(inputData.getString("URL"))

        val result = StringBuilder()

        try {
            urlConnection = url.openConnection() as HttpURLConnection?
            urlConnection?.doInput = true
            urlConnection?.connectTimeout = 20 * 100
            urlConnection?.readTimeout = 20 * 100

            if (urlConnection?.responseCode == HttpURLConnection.HTTP_OK) {
                val stream = BufferedInputStream(urlConnection?.inputStream)
                val reader = BufferedReader(InputStreamReader(stream))

                var line : String?

                do {
                    line = reader.readLine()
                    if (line == null) break
                    result.append(line)
                } while (true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect()
        }

        val outputData = Data.Builder().putString("response", result.toString()).build()
        Thread.sleep(5000)
        return Result.success(outputData)
    }

}

class DownloadImageWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private var urlConnection: HttpURLConnection? = null

    override fun doWork(): Result {

        val url = URL(inputData.getString("URL"))

        val result = StringBuilder()

        try {
            urlConnection = url.openConnection() as HttpURLConnection?
            urlConnection?.doInput = true
            urlConnection?.connectTimeout = 20 * 100
            urlConnection?.readTimeout = 20 * 100

            if (urlConnection?.responseCode == HttpURLConnection.HTTP_OK) {
                val stream = BufferedInputStream(urlConnection?.inputStream)

                val imagesZIP = File(applicationContext.filesDir, "images.zip")
                imagesZIP.createNewFile()

                val out = FileOutputStream(imagesZIP)
                copy(stream, out, 1024)
                out.close()

            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect()
        }

        val outputData = Data.Builder().putString("response", result.toString()).build()

        return Result.success(outputData)
    }

}

fun setUpDownloadImagesWorker(context: AppCompatActivity){
    WorkManager.getInstance(context).apply {
        getWorkInfoByIdLiveData(downloadImages.id)
            .observe(context, {
                when (it.state.isFinished) {
                    true -> {
                        val builder = NotificationCompat.Builder(context, "NOTIFICATION_CHANNEL")
                            .setSmallIcon(R.drawable.ic_baseline_notifications)
                            .setContentTitle("Worker Status")
                            .setContentText("Downloaded Data")
                        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(4, builder.build())
                    }
                    else -> {
                        val builder = NotificationCompat.Builder(context, "NOTIFICATION_CHANNEL")
                            .setSmallIcon(R.drawable.ic_baseline_notifications)
                            .setContentTitle("Worker Status")
                            .setContentText("Downloading Data")
                        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(3, builder.build())
                    }
                }
            })
    }
}

fun setUpDownloadWorker(context: AppCompatActivity, cities: ArrayList<City>){
    WorkManager.getInstance(context).apply {

        getWorkInfoByIdLiveData(download.id)
            .observe(context, {
                when (it.state.isFinished) {
                    true -> {
                        val data = it.outputData.getString("response")
                        val citiesDownloaded = Gson().fromJson<ArrayList<City>>(data, object : TypeToken<ArrayList<City>>() {}.type)

                        cities.addAll(citiesDownloaded)

                        val builder = NotificationCompat.Builder(context, "NOTIFICATION_CHANNEL")
                            .setSmallIcon(R.drawable.ic_baseline_notifications)
                            .setContentTitle("Worker Status")
                            .setContentText("Imagens baixadas")
                        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(2, builder.build())
                    }
                    else -> {
                        val builder = NotificationCompat.Builder(context, "NOTIFICATION_CHANNEL")
                            .setSmallIcon(R.drawable.ic_baseline_notifications)
                            .setContentTitle("Worker Status")
                            .setContentText("Baixando imagens")
                        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(1, builder.build())
                    }
                }
            })
    }
}

fun initWorkManagers(context: AppCompatActivity){
    WorkManager.getInstance(context).apply {
        beginWith(downloadImages)
            .then(unzipWorker)
            .then(download)
            .enqueue()
    }
}

@Throws(IOException::class)
fun copy(input: InputStream, output: OutputStream, bufferSize: Int) {
    val buf = ByteArray(bufferSize)
    var n = input.read(buf)
    while (n >= 0) {
        output.write(buf, 0, n)
        n = input.read(buf)
    }
    output.flush()
}