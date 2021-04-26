package com.hks.kr.wifireminder.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.hks.kr.wifireminder.domain.repository.TaskRepository
import com.hks.kr.wifireminder.workers.WifiWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val app: Application,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val wifiWorker = WorkManager.getInstance(app)

    internal fun showWifiSSid() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val oneTimeJobWithNetwork = OneTimeWorkRequest.Builder(WifiWorker::class.java)
            .setConstraints(constraints).build()

        wifiWorker.enqueue(oneTimeJobWithNetwork)
    }

}