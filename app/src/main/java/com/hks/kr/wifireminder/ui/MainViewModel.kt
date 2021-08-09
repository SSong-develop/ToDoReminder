package com.hks.kr.wifireminder.ui

import android.app.Application
import androidx.lifecycle.*
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
) : ViewModel(),LifecycleObserver {
    private val _isSingleInvoked = MutableLiveData<Boolean>()
    val isSingleInvoked: LiveData<Boolean>
        get() = _isSingleInvoked

    private val _addTaskVisibility = MutableLiveData<Boolean>()
    val addTaskVisibility: LiveData<Boolean>
        get() = _addTaskVisibility

    init {
        showFab()
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

    fun setFabVisible(value : Boolean){
        _addTaskVisibility.value = value
    }

    private fun showFab(){
        _addTaskVisibility.value = true
    }
}