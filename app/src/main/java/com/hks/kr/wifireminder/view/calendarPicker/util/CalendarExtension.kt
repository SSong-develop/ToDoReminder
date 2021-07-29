package com.hks.kr.wifireminder.view.calendarPicker.util

import java.util.*

/**
 * 특정 날짜와 비교하고자 하는 날짜가 동일한 날인지를 확인하는 함수
 */
fun Date.isTheSameDay(comparedDate: Date): Boolean {
    val calendar = Calendar.getInstance()
    calendar.withTime(this)
    val comparedCalendarDate = Calendar.getInstance()
    comparedCalendarDate.withTime(comparedDate)
    return calendar.get(Calendar.DAY_OF_YEAR) == comparedCalendarDate.get(Calendar.DAY_OF_YEAR) && calendar.get(
        Calendar.YEAR
    ) == comparedCalendarDate.get(Calendar.YEAR)
}

/**
 * Calendar에 time을 setting해주는 함수
 */
fun Calendar.withTime(date: Date) {
    clear()
    time = date
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}