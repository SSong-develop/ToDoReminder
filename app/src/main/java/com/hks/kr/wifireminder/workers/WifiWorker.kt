package com.hks.kr.wifireminder.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hks.kr.wifireminder.utils.makeNotification

class WifiWorker constructor(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            makeNotification("wifi connected!!!",applicationContext)
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
            throw exception
        }
    }

}