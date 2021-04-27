package com.hks.kr.wifireminder.workers

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hks.kr.wifireminder.api.local.database.TaskDao
import com.hks.kr.wifireminder.utils.sendTaskNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class TaskNotificationWork @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(context, params) {

    @Inject
    lateinit var taskDao: TaskDao

    override suspend fun doWork(): Result {
        return try {
            val notificationManager = ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java
            ) as NotificationManager

            val stringBuilder = StringBuilder()
            taskDao.fetchTaskSortByImportance().forEach {
                stringBuilder.append("${it}\n")
            }
            notificationManager.sendTaskNotification(stringBuilder.toString(), applicationContext)
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
            throw exception
        }
    }

}