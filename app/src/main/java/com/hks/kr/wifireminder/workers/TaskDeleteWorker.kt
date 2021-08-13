package com.hks.kr.wifireminder.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hks.kr.wifireminder.data.source.local.dao.TaskDao
import com.hks.kr.wifireminder.utils.DateUtil
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*
import javax.inject.Inject

@HiltWorker
class TaskDeleteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {

    @Inject
    lateinit var taskDao: TaskDao

    override suspend fun doWork(): Result {
        return try {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE,1)
            val tomorrow = DateUtil.dateFormatBarWithOutTime.format(calendar.time)
            taskDao.getTasks().forEach {
                if(it.endDate == tomorrow)
                    taskDao.deleteTaskById(it.id)
            }
            Result.success()
        }catch (e : Exception) {
            Result.failure()
            throw e
        }
    }

}