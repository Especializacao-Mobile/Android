package br.imaginefree.city.bg

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import br.imaginefree.city.R

fun createChannelNotifications(context: AppCompatActivity) {
    val channel = NotificationChannel(
        context.getString(R.string.notification_channel),
        context.getString(R.string.work_manager_app),
        NotificationManager.IMPORTANCE_DEFAULT
    )
    channel.description = context.getString(R.string.hello_daivid)

    (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        .createNotificationChannel(channel)
}