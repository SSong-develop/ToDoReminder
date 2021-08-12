package com.hks.kr.wifireminder.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private val oneDayTime by lazy {
        24 * 60 * 60 * 1000
    }

    val dateFormatBarWithOutTime by lazy {
        SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    }

    fun countDday(date: Date): Long = try {
        val todayCalendar = Calendar.getInstance()
        val dDayCalendar = Calendar.getInstance()
        dDayCalendar.time = date

        val todayTime = todayCalendar.timeInMillis
        val dDayTime = dDayCalendar.timeInMillis

        ((dDayTime - todayTime) / oneDayTime) + 1
    } catch (e: Exception) {
        debugE(e.message)
        -1
    }
}