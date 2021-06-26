package com.hks.kr.wifireminder.view.calendar.ui

import android.view.View
import com.hks.kr.wifireminder.view.calendar.model.CalendarDay
import com.hks.kr.wifireminder.view.calendar.model.CalendarMonth

open class ViewContainer(val view: View)

interface DayBinder<T : ViewContainer> {
    fun create(view: View): T
    fun bind(container: T, day: CalendarDay)
}

interface MonthHeaderFooterBinder<T : ViewContainer> {
    fun create(view : View) : T
    fun bind(container : T , month : CalendarMonth)
}

typealias MonthScrollListener = (CalendarMonth) -> Unit