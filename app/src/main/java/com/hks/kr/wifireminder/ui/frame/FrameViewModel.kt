package com.hks.kr.wifireminder.ui.frame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FrameViewModel @Inject constructor() :
    ViewModel() {
    private val _pageIdx: MutableLiveData<Int> = MutableLiveData(0)
    val pageIdx: LiveData<Int> = _pageIdx

    fun onPageSelected(idx: Int) {
        if (pageIdx.value != idx) _pageIdx.value = idx
    }
}