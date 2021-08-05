package com.hks.kr.wifireminder.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hks.kr.wifireminder.datastore.PrefsStore
import com.hks.kr.wifireminder.workers.TaskNotificationWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * TODO : 기간이 지난 TASK들을 지워주는 WORKMANAGER를 만들어야 합니다!!!
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val app: Application,
    private val prefsStore: PrefsStore
) : ViewModel() {
    private val _isSingleInvoked = MutableLiveData<Boolean>()
    val isSingleInvoked: LiveData<Boolean>
        get() = _isSingleInvoked

    init {
        getResultOfSingleInvoked()
    }

    fun initializeNotificationWorker() {
        WorkManager.getInstance(app).enqueueUniquePeriodicWork(
            "TaskNotificationWork",
            ExistingPeriodicWorkPolicy.REPLACE,
            PeriodicWorkRequestBuilder<TaskNotificationWorker>(6, TimeUnit.HOURS).build()
        )
    }

    private fun getResultOfSingleInvoked() {
        viewModelScope.launch {
            prefsStore.isAlreadySingleInvokedWithFlow().collect {
                _isSingleInvoked.value = it
            }
        }
    }

    fun runSingleInvoke(block: () -> Unit) {
        viewModelScope.launch {
            prefsStore.onSingleInvoke()
        }
        block()
    }

}