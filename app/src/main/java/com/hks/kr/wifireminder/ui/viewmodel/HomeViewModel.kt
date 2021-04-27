package com.hks.kr.wifireminder.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.hks.kr.wifireminder.domain.repository.TaskRepository
import com.hks.kr.wifireminder.workers.TaskNotificationWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val app: Application,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val wifiWorker = WorkManager.getInstance(app)
    private val taskNotificationWorkter = WorkManager.getInstance(app)

    internal fun testShowWifiConnect() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            TaskNotificationWorker::class.java, 15, TimeUnit.MINUTES
        ).build()

        // PeriodWorkRequest로 변경해서 3시간마다 1번씩 할일들을 보여주는 푸시알림을 보내준다.
        taskNotificationWorkter.enqueue(periodicWorkRequest)
    }

}