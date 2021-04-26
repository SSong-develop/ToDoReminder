package com.hks.kr.wifireminder.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.application.MainApplication.Companion.versionCheckUtils

// ViewModel에서 사용될 예정인 이녀석들은 전부 applicationContext를 받게 됩니다.
fun makeNotification(message: String, context: Context) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (versionCheckUtils.isVersionUpToOreo()) {
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

fun getWifiSSId(context: Context): String = "need To Fix it :("