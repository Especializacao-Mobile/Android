package br.imaginefree.city.bg

import android.app.NotificationManager
import android.content.Context
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.imaginefree.city.*
import br.imaginefree.city.feature.adapter.CityAdapter
import br.imaginefree.city.model.City
import br.imaginefree.city.model.Promotions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.ZipFile

class UnzipWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

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
            urlConnection?.connectTimeout = 3000
            urlConnection?.readTimeout = 3000

            if (urlConnection?.responseCode == HttpURLConnection.HTTP_OK) {
                val stream = BufferedInputStream(urlConnection?.inputStream)
                val reader = BufferedReader(InputStreamReader(stream))

                var line: String?

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
            urlConnection?.connectTimeout = 3000
            urlConnection?.readTimeout = 3000

            if (urlConnection?.responseCode == HttpURLConnection.HTTP_OK) {
                val stream = BufferedInputStream(urlConnection?.inputStream)

                val imagesZIP = File(applicationContext.filesDir, "images.zip")
                imagesZIP.createNewFile()

                val out = FileOutputStream(imagesZIP)
                val buf = ByteArray(1024)
                var n = stream.read(buf)
                while (n >= 0) {
                    out.write(buf, 0, n)
                    n = stream.read(buf)
                }
                out.flush()
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

fun setUpDownloadImagesWorker(context: AppCompatActivity) {
    WorkManager.getInstance(context).apply {
        getWorkInfoByIdLiveData(downloadImages.id)
            .observe(context, {
                when (it.state.isFinished) {
                    true -> {
                        val builder = NotificationCompat.Builder(
                            context,
                            context.getString(R.string.notification_channel)
                        )
                            .setSmallIcon(R.drawable.ic_baseline_notifications)
                            .setContentTitle(notificationWarning)
                            .setContentText(context.getString(R.string.downloaded_data))
                        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(
                            4,
                            builder.build()
                        )
                    }
                    else -> {
                        val builder = NotificationCompat.Builder(
                            context,
                            context.getString(R.string.notification_channel)
                        )
                            .setSmallIcon(R.drawable.ic_baseline_notifications)
                            .setContentTitle(notificationWarning)
                            .setContentText(context.getString(R.string.doanloading_data))
                        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(
                            3,
                            builder.build()
                        )
                    }
                }
            })
    }
}

fun setUpDownloadWorker(context: AppCompatActivity, cities: ArrayList<City>, adapter: CityAdapter) {
    WorkManager.getInstance(context).apply {

        getWorkInfoByIdLiveData(download.id)
            .observe(context, {
                when (it.state.isFinished) {
                    true -> {
                        val data = it.outputData.getString("response")
                        val citiesDownloaded = Gson().fromJson<Promotions>(
                            data,
                            object : TypeToken<Promotions>() {}.type
                        )

                        cities.addAll(citiesDownloaded.pacotes)
                        adapter.notifyDataSetChanged()

                        val builder = NotificationCompat.Builder(
                            context,
                            context.getString(R.string.notification_channel)
                        )
                            .setSmallIcon(R.drawable.ic_baseline_notifications)
                            .setContentTitle(notificationWarning)
                            .setContentText(context.getString(R.string.downloaded_images))
                        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(
                            2,
                            builder.build()
                        )
                    }
                    else -> {
                        val builder = NotificationCompat.Builder(
                            context,
                            context.getString(R.string.notification_channel)
                        )
                            .setSmallIcon(R.drawable.ic_baseline_notifications)
                            .setContentTitle(notificationWarning)
                            .setContentText(context.getString(R.string.downloading_images))
                        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(
                            1,
                            builder.build()
                        )
                    }
                }
            })
    }
}

fun initWorkManagers(context: AppCompatActivity) {
    WorkManager.getInstance(context).apply {
        beginWith(downloadImages)
            .then(unzipWorker)
            .then(download)
            .enqueue()
    }
}
