package com.hks.kr.wifireminder.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.data.CategoryDTO
import com.hks.kr.wifireminder.datastore.PrefsStore
import com.hks.kr.wifireminder.domain.repository.CategoryRepository
import com.hks.kr.wifireminder.workers.TaskDeleteWorker
import com.hks.kr.wifireminder.workers.TaskNotificationWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val app: Application,
    private val categoryRepository: CategoryRepository,
    private val prefsStore: PrefsStore
) : ViewModel() {

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

    fun initializeDeleteWorker() {
        WorkManager.getInstance(app).enqueueUniquePeriodicWork(
            "TaskDeletionWork",
            ExistingPeriodicWorkPolicy.REPLACE,
            PeriodicWorkRequestBuilder<TaskDeleteWorker>(15,TimeUnit.MINUTES).build()
        )
    }

    fun initializeDefaultCategories() = viewModelScope.launch {
        categoryRepository.insertCategory(
            CategoryDTO(
                categoryTitle = "Life",
                backgroundColorCode = "#AA11AA",
                icon = R.drawable.ic_life
            )
        )
        categoryRepository.insertCategory(
            CategoryDTO(
                categoryTitle = "Work",
                backgroundColorCode = "#4D6B8FF9",
                icon = R.drawable.ic_work
            )
        )
        categoryRepository.insertCategory(
            CategoryDTO(
                categoryTitle = "Fun",
                backgroundColorCode = "#4DAA11AA",
                icon = R.drawable.ic_fun
            )
        )
        categoryRepository.insertCategory(
            CategoryDTO(
                categoryTitle = "ETC",
                backgroundColorCode = "#6B8FF9",
                icon = R.drawable.ic_etc
            )
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

    fun setFabVisible(value: Boolean) {
        _addTaskVisibility.value = value
    }

    private fun showFab() {
        _addTaskVisibility.value = true
    }
}