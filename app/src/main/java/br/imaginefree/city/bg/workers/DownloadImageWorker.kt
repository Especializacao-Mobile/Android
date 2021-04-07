package br.imaginefree.city.bg.workers

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

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