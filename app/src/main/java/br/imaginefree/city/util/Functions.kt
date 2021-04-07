package br.imaginefree.city.util

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.work.WorkManager
import br.imaginefree.city.R
import br.imaginefree.city.feature.adapter.CityAdapter
import br.imaginefree.city.model.City
import br.imaginefree.city.model.Promotions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun initWorkManagers(context: AppCompatActivity) {
    WorkManager.getInstance(context).apply {
        beginWith(downloadImages)
            .then(unzipWorker)
            .then(download)
            .enqueue()
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

fun setUpDownloadWorker(
    context: AppCompatActivity,
    update: (cities: ArrayList<City>) -> Unit,
    error: () -> Unit
) {
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
                        citiesDownloaded?.let { promotions ->
                            update(promotions.pacotes)
                        } ?: run {
                            error()
                        }

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
