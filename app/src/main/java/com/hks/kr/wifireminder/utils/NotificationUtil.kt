package com.hks.kr.wifireminder.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.application.MainApplication
import com.hks.kr.wifireminder.application.MainApplication.Companion.sampleKeystore
import com.hks.kr.wifireminder.ui.presentation.activity.HomeActivity

fun NotificationManager.sendNotification(
    messageBody: String,
    applicationContext: Context
) {
    val contentIntent = applicationContext.getIntent<HomeActivity>()

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        sampleKeystore.provideNotificationID(),
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.task_notification_channel_id)
    )
        .setContentTitle(applicationContext.getString(R.string.task_notification_title))
        .setContentText(messageBody) // 여기 text에 해야할 일들의 목록들이 쫘르르륵 나와야 함
        .setContentIntent(contentPendingIntent)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    notify(sampleKeystore.provideNotificationID(), builder.build())
}

// test 함수
fun makeNotification(message: String, context: Context) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (MainApplication.versionCheckUtils.isVersionUpToOreo()) {
        val name = context.getString(R.string.task_notification_channel_name)
        val description = context.getString(R.string.task_notification_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(
            context.getString(R.string.task_notification_channel_id),
            name,
            importance
        )
        channel.description = description

        notificationManager.createNotificationChannel(channel)
    }
    notificationManager.sendNotification(message, context)
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}