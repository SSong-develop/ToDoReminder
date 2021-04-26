package com.hks.kr.wifireminder.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class WifiWorker constructor(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            /*NetworkConnectChecker(applicationContext).registerNetworkCallback()*/
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
            throw exception
        }
    }

}