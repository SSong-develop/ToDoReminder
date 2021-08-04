package com.hks.kr.wifireminder.view.calendarPicker

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.view.calendarPicker.adapter.CalendarAdapter
import com.hks.kr.wifireminder.view.calendarPicker.entity.CalendarEntity
import com.hks.kr.wifireminder.view.calendarPicker.entity.SelectionMode
import com.hks.kr.wifireminder.view.calendarPicker.entity.TOTAL_COLUMN_COUNT
import com.hks.kr.wifireminder.view.calendarPicker.util.isTheSameDay
import com.hks.kr.wifireminder.view.calendarPicker.util.withTime
import java.util.*
import java.util.Calendar.*

class CalendarPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : RecyclerView(context, attrs) {

    // TimeZone
    private val timeZone = TimeZone.getDefault()

    // 한국 날짜
    private val locale = Locale.KOREA

    // 년도 , 달 , 일을 보여줄 어댑터
    private val calendarAdapter = CalendarAdapter()

    // 이번 날짜부터 보여줄 캘린더
    private val startCalendar = getInstance(timeZone, locale)

    // 마지막 날짜를 보여줄 캘린더
    private val endCalendar = getInstance(timeZone, locale)

    // 월별 일자 데이터를 모아놓는 리스트
    private var calendarData: MutableList<CalendarEntity> = mutableListOf()

    // 선택된 시작 Date
    private var startDateSelection: SelectedDate? = null

    // 선택된 끝 Date
    private var endDateSelection: SelectedDate? = null

    // Picker Mode
    private var pickerSelectionType = SelectionMode.RANGE

    // 선택 Listener
    private var onStartSelectedListener: (startDate: Date, label: String) -> Unit = { _, _ -> }

    // 범위 선택 Listener
    private var onRangeSelectedListener: (startDate: Date, endDate: Date, startLabel: String, endLabel: String) -> Unit =
        { _, _, _, _ -> }

    init {
        startCalendar.set(HOUR_OF_DAY, 0)
        startCalendar.set(MINUTE, 0)
        startCalendar.set(SECOND, 0)
        startCalendar.set(MILLISECOND,0)

        endCalendar.time = startCalendar.time
        endCalendar.add(YEAR,1)

        setBackgroundColor(ContextCompat.getColor(context, R.color.calendar_picker_bg_color))

        initAdapter()
        initListener()
    }

    fun setRangeDate(startDate : Date , endDate : Date) {
        require(startDate.time <= endDate.time) { "startDate can't be higher than endDate"}

        startCalendar.withTime(startDate)
        endCalendar.withTime(endDate)

        refreshData()
    }

    fun scrollToDate(date : Date) {
        val index = calendarData.indexOfFirst { it is CalendarEntity.Day && it.date.isTheSameDay(date) }
        require(index > -1) { "Date to scroll must be included in your Calendar Range Date"}
        smoothScrollToPosition(index)
    }

    fun setSelectionDate(startDate : Date , endDate : Date? = null) {
        itemAnimator = null
        selectDate(startDate)
        if(endDate != null) selectDate(endDate)
    }

    /**
     * CalendarPicker Select Mode 설정해주는 함수
     */
    fun setMode(mode : SelectionMode) {
        pickerSelectionType = mode
    }

    fun setOnStartSelectedListener(callback : (startDate : Date , label : String) -> Unit) {
        onStartSelectedListener = callback
    }

    fun setOnRangeSelectedListener(callback : (startDate : Date , endDate : Date , startLabel : String , endLabel : String) -> Unit) {
        onRangeSelectedListener = callback
    }

    private fun initListener() {
        calendarAdapter.onActionListener = {item,position ->
            if(itemAnimator == null) itemAnimator = DefaultItemAnimator()
            if(item is CalendarEntity.Day) onDaySelected(item,position)
        }
    }

    private fun initAdapter() {
        layoutManager = GridLayoutManager(context, TOTAL_COLUMN_COUNT).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return calendarData[position].columnCount
                }
            }
        }
        adapter = calendarAdapter
        refreshData()
    }

    data class SelectedDate(val day: CalendarEntity.Day, val position: Int)
}