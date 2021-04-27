package com.hks.kr.wifireminder.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.application.MainApplication.Companion.sampleKeystore
import com.hks.kr.wifireminder.application.MainApplication.Companion.versionCheckUtils
import com.hks.kr.wifireminder.ui.presentation.activity.HomeActivity

fun NotificationManager.sendTaskNotification(
    messageBody: String,
    applicationContext: Context
) {
    val contentIntent = applicationContext.getIntent<HomeActivity>()

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        sampleKeystore.provideTaskNotificationID(),
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.task_notification_channel_id)
    )
        .setContentTitle(applicationContext.getString(R.string.task_notification_title))
        .setContentText(applicationContext.getString(R.string.task_notification_text))
        .setContentIntent(contentPendingIntent)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    notify(sampleKeystore.provideTaskNotificationID(), builder.build())
}

fun NotificationManager.sendWifiNotification(
    messageBody: String,
    applicationContext: Context
) {
    val contentIntent = applicationContext.getIntent<HomeActivity>()

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        sampleKeystore.provideWifiNotificationID(),
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.wifi_notification_channel_id)
    )
        .setContentTitle(applicationContext.getString(R.string.wifi_notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    notify(sampleKeystore.provideWifiNotificationID(), builder.build())

}

fun NotificationManager.createTaskNotificationChannel(context: Context) {
    if (versionCheckUtils.isVersionUpToOreo()) {
        val channelName = context.getString(R.string.task_notification_channel_name)
        val channelDescription = context.getString(R.string.task_notification_description)
        val channelImportance = IMPORTANCE_HIGH
        val channel = NotificationChannel(
            context.getString(R.string.task_notification_channel_id),
            channelName,
            channelImportance
        )
        channel.description = channelDescription
        createNotificationChannel(channel)
    }
}

fun NotificationManager.createWifiNotificationChannel(context: Context) {
    if (versionCheckUtils.isVersionUpToOreo()) {
        val wifiChannelName = context.getString(R.string.wifi_notification_channel_name)
        val wifiChannelDescription =
            context.getString(R.string.wifi_notification_channel_description)
        val wifiChannelImportance = IMPORTANCE_DEFAULT
        val wifiChannel = NotificationChannel(
            context.getString(R.string.wifi_notification_channel_id),
            wifiChannelName,
            wifiChannelImportance
        )
        wifiChannel.description = wifiChannelDescription
        createNotificationChannel(wifiChannel)
    }
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}