package com.hks.kr.wifireminder.workers

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hks.kr.wifireminder.utils.sendTaskNotification

class TaskNotificationWorker constructor(context: Context, params: WorkerParameters) :
    Worker(context, params) {

    override fun doWork(): Result {
        return try {
            val notificationManager = ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java
            ) as NotificationManager

            notificationManager.sendTaskNotification("hello", applicationContext)
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
            throw exception
        }
    }

}