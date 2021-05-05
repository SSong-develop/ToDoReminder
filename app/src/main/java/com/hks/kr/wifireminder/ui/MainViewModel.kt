package com.hks.kr.wifireminder.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hks.kr.wifireminder.datastore.PrefsStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefsStore: PrefsStore
) : ViewModel() {
    private val _isSingleInvoked = MutableLiveData<Boolean>()
    val isSingleInvoked: LiveData<Boolean>
        get() = _isSingleInvoked

    init {
        getResultOfSingleInvoked()
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