package com.hks.kr.wifireminder.view.calendarPicker.util

import java.util.*
import java.util.Calendar.*

/**
 * 클릭 시 달력의 날짜를 가져올 때, string format으로 가져오는 함수
 */
fun Calendar.toPrettyMonthString(
    style: Int = LONG,
    locale: Locale = Locale.getDefault()
): String {
    val month = getDisplayName(MONTH, style, locale)
    val year = get(YEAR).toString()
    return if (month == null) throw IllegalStateException("Cannot get pretty name")
    else "$month $year"
}

/**
 * 클릭 시 달력의 날짜를 가져올 때, string format으로 가져오는 함수
 */
fun Calendar.toPrettyDateString(locale: Locale = Locale.getDefault()): String {
    val month = (get(MONTH) + 1).toString()
    val year = get(YEAR).toString()
    val day = get(DAY_OF_MONTH).toString()
    return "$year.$month.$day"
}

/**
 * 현재 날짜로부터 날이 이미 지났는지를 알아보는 함수
 */
fun Calendar.isBefore(otherCalendar: Calendar): Boolean {
    return get(YEAR) == otherCalendar.get(YEAR)
            && get(MONTH) == otherCalendar.get(MONTH)
            && get(DAY_OF_MONTH) < otherCalendar.get(DAY_OF_MONTH)
}

/**
 * 현재 날짜로 부터 이미 날이 지난 만큼 뒤에 날들을 채워줘야하는 그때 사용하는 함수
 */
fun Calendar.isAfter(otherCalendar: Calendar): Boolean {
    return get(YEAR) == otherCalendar.get(YEAR)
            && get(MONTH) == otherCalendar.get(MONTH)
            && get(DAY_OF_MONTH) > otherCalendar.get(DAY_OF_MONTH)
}

/**
 * ???
 */
fun Calendar.totalMonthDifference(startCalendar : Calendar) : Int {
    val yearDiff = get(YEAR) - startCalendar.get(YEAR)
    val monthDiff = get(MONTH) - startCalendar.get(MONTH)

    return monthDiff + (yearDiff * 12)
}

/**
 * 주말인지 아닌지 알려주는 함수
 */
fun Calendar.isWeekend() : Boolean {
    return get(DAY_OF_WEEK) == SATURDAY || get(DAY_OF_WEEK) == SUNDAY
}