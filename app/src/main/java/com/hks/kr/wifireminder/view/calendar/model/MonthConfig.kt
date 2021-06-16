package com.hks.kr.wifireminder.view.calendar.model

import kotlinx.coroutines.Job
import java.time.DayOfWeek
import java.time.YearMonth

internal data class MonthConfig(
    internal val outDateStyle : OutDateStyle,
    internal val inDateStyle : InDateStyle,
    internal val maxRowCount : Int,
    internal val startMonth : YearMonth,
    internal val endMonth : YearMonth,
    internal val firstDayOfWeek: DayOfWeek,
    internal val hasBoundaries : Boolean,
    internal val job : Job
) {

    internal companion object {

        private val uninterruptedJob = Job()

        /**
         * A [YearMonth] will have multiple [CalendarMonth] instance if the [maxRowCount] is
         * less than 6. Each [CalendarMonth] will hold just enough [CalendarDay] instances(weekDays)
         * to fit in the [maxRowCount]
         */

        // TODO : I NEED TO START IN HERE!!
    }
}
