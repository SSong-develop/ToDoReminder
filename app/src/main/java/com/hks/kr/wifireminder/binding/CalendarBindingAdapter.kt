package com.hks.kr.wifireminder.binding

import androidx.databinding.BindingAdapter
import com.hks.kr.wifireminder.ui.addTask.AddTaskViewModel
import com.hks.kr.wifireminder.view.calendarPicker.CalendarPicker

object CalendarBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:setOnRangeSelectListener")
    fun setOnRangeSelectListener(calendarPicker: CalendarPicker , viewModel : AddTaskViewModel) {
        calendarPicker.setOnRangeSelectedListener { startDate, endDate, startLabel, endLabel ->
            viewModel.date.value = Pair(startLabel,endLabel)
        }
    }
}