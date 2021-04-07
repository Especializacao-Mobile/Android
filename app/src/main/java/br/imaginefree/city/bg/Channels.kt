package br.imaginefree.city.bg

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

fun createChannelNotifications(context: AppCompatActivity){
    val channel = NotificationChannel(
        "NOTIFICATION_CHANNEL",
        "WORK_MANAGER_APP",
        NotificationManager.IMPORTANCE_DEFAULT
    )
    channel.description = "Hello Daivid!"

    (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        .createNotificationChannel(channel)
}