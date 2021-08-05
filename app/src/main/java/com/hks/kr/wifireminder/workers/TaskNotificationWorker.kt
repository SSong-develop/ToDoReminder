package com.hks.kr.wifireminder.workers

import android.app.NotificationManager
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hks.kr.wifireminder.api.local.database.TaskDao
import com.hks.kr.wifireminder.utils.sendTaskNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class TaskNotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(context, params) {

    @Inject
    lateinit var taskDao: TaskDao

    @Inject
    lateinit var notificationManager: NotificationManager

    override suspend fun doWork(): Result {
        return try {
            val stringBuilder = StringBuilder()
            taskDao.fetchTaskSortByImportance().forEach {
                stringBuilder.append(" - ${it.taskName}\n")
            }
            notificationManager.sendTaskNotification(stringBuilder.toString(), applicationContext)
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
            throw exception
        }
    }

}