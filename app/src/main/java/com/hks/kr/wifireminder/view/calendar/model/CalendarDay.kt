package com.hks.kr.wifireminder.view.calendar.model

import android.os.Parcelable
import com.hks.kr.wifireminder.view.calendar.extension.next
import com.hks.kr.wifireminder.view.calendar.extension.previous
import com.hks.kr.wifireminder.view.calendar.extension.yearMonth
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.YearMonth

@Parcelize
data class CalendarDay internal constructor(val date: LocalDate, val owner: DayOwner) :
    Parcelable, Comparable<CalendarDay> {

    val day = date.dayOfMonth

    // Find the actual month on the calendar that owns this date.
    internal val positionYearMonth: YearMonth
        get() = when (owner) {
            DayOwner.THIS_MONTH -> date.yearMonth
            DayOwner.PREVIOUS_MONTH -> date.yearMonth.next
            DayOwner.NEXT_MONTH -> date.yearMonth.previous
        }

    override fun toString(): String {
        return "CalendarDay { date = $date , owner = $owner}"
    }

    override fun compareTo(other: CalendarDay): Int {
        throw UnsupportedOperationException(
            "Compare using the `date` parameter instead." +
                    "Out and In dates can have the same date values as CalendarDay in another month."
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CalendarDay
        return date == other.date && owner == other.owner
    }

    override fun hashCode(): Int {
        return 31 * (date.hashCode()) + owner.hashCode()
    }
}