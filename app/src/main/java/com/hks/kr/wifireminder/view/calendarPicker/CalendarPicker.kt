package com.hks.kr.wifireminder.view.calendarPicker

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.view.calendarPicker.adapter.CalendarAdapter
import com.hks.kr.wifireminder.view.calendarPicker.entity.*
import com.hks.kr.wifireminder.view.calendarPicker.util.*
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
        /**
         * initialize startCalendar
         */
        startCalendar.set(HOUR_OF_DAY, 0)
        startCalendar.set(MINUTE, 0)
        startCalendar.set(SECOND, 0)
        startCalendar.set(MILLISECOND, 0)

        /**
         * initialize endCalendar
         */
        endCalendar.time = startCalendar.time
        endCalendar.add(YEAR, 1)

        setBackgroundColor(ContextCompat.getColor(context, R.color.calendar_picker_bg_color))

        overScrollMode = OVER_SCROLL_NEVER
        initAdapter()
        initListener()
    }

    /**
     * initialize Listener
     */
    private fun initListener() {
        calendarAdapter.onActionListener = { item, position ->
            if (itemAnimator == null) itemAnimator = DefaultItemAnimator()
            if (item is CalendarEntity.Day) onDaySelected(item, position)
        }
    }

    /**
     * initialize Adapter
     */
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


    fun setRangeDate(startDate: Date, endDate: Date) {
        require(startDate.time <= endDate.time) { "startDate can't be higher than endDate" }

        startCalendar.withTime(startDate)
        endCalendar.withTime(endDate)

        refreshData()
    }

    fun scrollToDate(date: Date) {
        val index =
            calendarData.indexOfFirst { it is CalendarEntity.Day && it.date.isTheSameDay(date) }
        require(index > -1) { "Date to scroll must be included in your Calendar Range Date" }
        smoothScrollToPosition(index)
    }

    /**
     * Select Specific Date
     */
    fun setSelectionDate(startDate: Date, endDate: Date? = null) {
        itemAnimator = null // 애니메이터 해제
        selectDate(startDate) // selectDate함수 호출
        if (endDate != null) selectDate(endDate) // 만약 endDate가 null이 아니라면 endDate도 select해준다.
        // TODO : Range와 single 2개의 모드가 있어서 이렇게 처리한거 같은데 우리는 현재 Range만을 지원할 것이기 때문에 바꿔줘야 한다.
    }

    /**
     * CalendarPicker Select Mode 설정해주는 함수
     * TODO : Range만을 지원할 거라서 사실 필요없음
     */
    fun setMode(mode: SelectionMode) {
        pickerSelectionType = mode
    }

    /**
     * set StartSelectedListener
     */
    fun setOnStartSelectedListener(callback: (startDate: Date, label: String) -> Unit) {
        onStartSelectedListener = callback
    }

    /**
     * set RangeSelectedListener
     */
    fun setOnRangeSelectedListener(callback: (startDate: Date, endDate: Date, startLabel: String, endLabel: String) -> Unit) {
        onRangeSelectedListener = callback
    }

    /**
     * get Selected RangeDate
     */
    fun getSelectedDate(): Pair<Date?, Date?> {
        return Pair(startDateSelection?.day?.date, endDateSelection?.day?.date)
    }

    /**
     * Date Select
     */
    private fun selectDate(date: Date) {
        // 선택하고자 하는 Date가 CalendarData의 몇번쨰 index에 있는지를 확인
        val index =
            calendarData.indexOfFirst { it is CalendarEntity.Day && it.date.isTheSameDay(date) }
        require(index > -1) { // -1일 경우, 즉 없는 경우에는 error를 반환
            "Selection date must be included in your Calendar Range Date"
        }

        // index가 존재하는 경우 onDaySelected함수를 호출
        onDaySelected(calendarData[index] as CalendarEntity.Day, index)
    }

    private fun refreshData() {
        calendarData = buildCalendarData()
        calendarAdapter.submitList(calendarData)
    }

    /**
     * 각 월마다 1,2,3..일들을 calendarData에 담아두는 함수
     */
    private fun buildCalendarData(): MutableList<CalendarEntity> {
        val calendarData = mutableListOf<CalendarEntity>() // 일자들을 담아둘 list 선언
        val cal = getInstance(timeZone, locale) // 날짜들을 가져오기 위한 Calendar

        cal.withTime(startCalendar.time) // startCalendar로 time 설정
        // startCalendar와 endCalendar의 차이가 필요한데, 년도가 한차례 바뀐 경우가 있기 떄문에
        // totalMonthDifference 확장함수를 사용했다.
        val monthDifference = endCalendar.totalMonthDifference(startCalendar)

        // 1월부터 시작
        cal.set(DAY_OF_MONTH, 1)
        (0..monthDifference).forEach { _ -> // 각 월별을 나타내는 반복문
            val totalDayInMonth = cal.getActualMaximum(DAY_OF_MONTH)
            (1..totalDayInMonth).forEach { _ -> // 월별 일자들을 나타내는 반복문
                val day = cal.get(DAY_OF_MONTH)
                val dayOfYear = cal.get(DAY_OF_YEAR)
                val dayOfWeek = cal.get(DAY_OF_WEEK)
                val dateState = if (
                    cal.isBefore(startCalendar)
                    || cal.isAfter(endCalendar)
                ) {
                    DateState.DISABLED
                } else if (cal.isWeekend()) {
                    DateState.WEEKEND
                } else {
                    DateState.WEEKDAY
                }
                when (day) {
                    1 -> {
                        calendarData.add(
                            CalendarEntity.Month(
                                cal.toPrettyMonthString(),
                                dayOfYear.toString()
                            )
                        )
                        calendarData.addAll(createStartEmptyView(dayOfWeek))
                        calendarData.add(
                            CalendarEntity.Day(
                                day.toString(),
                                cal.toPrettyDateString(),
                                cal.time,
                                state = dateState
                            )
                        )
                    }
                    totalDayInMonth -> {
                        calendarData.add(
                            CalendarEntity.Day(
                                day.toString(),
                                cal.toPrettyDateString(),
                                cal.time,
                                state = dateState
                            )
                        )
                        calendarData.addAll(createEndEmptyView(dayOfWeek))
                    }
                    else -> {
                        calendarData.add(
                            CalendarEntity.Day(
                                day.toString(),
                                cal.toPrettyDateString(),
                                cal.time,
                                state = dateState
                            )
                        )
                    }
                }
                cal.add(DATE, 1)
            }
        }
        return calendarData
    }

    /**
     * 일자가 항상 월요일부터 시작하지 않기 때문에, 시작하지 않는 곳은 비어있는 곳으로 나타내줘야한다.
     */
    private fun createStartEmptyView(dayOfWeek: Int): List<CalendarEntity.Empty> {
        val numberOfEmptyView = when (dayOfWeek) {
            MONDAY -> 1
            TUESDAY -> 2
            WEDNESDAY -> 3
            THURSDAY -> 4
            FRIDAY -> 5
            SATURDAY -> 6
            else -> 0
        }

        val listEmpty = mutableListOf<CalendarEntity.Empty>()
        repeat((0 until numberOfEmptyView).count()) { listEmpty.add(CalendarEntity.Empty) }
        return listEmpty
    }

    private fun createEndEmptyView(dayOfWeek: Int): List<CalendarEntity.Empty> {
        val numberOfEmptyView = when (dayOfWeek) {
            SUNDAY -> 6
            MONDAY -> 5
            TUESDAY -> 4
            WEDNESDAY -> 3
            THURSDAY -> 2
            FRIDAY -> 1
            else -> 6
        }

        val listEmpty = mutableListOf<CalendarEntity.Empty>()
        repeat((0 until numberOfEmptyView).count()) { listEmpty.add(CalendarEntity.Empty) }
        return listEmpty
    }

    private fun onDaySelected(item: CalendarEntity.Day, position: Int) {
        if (item == startDateSelection?.day) return
        when {
            pickerSelectionType == SelectionMode.SINGLE -> {
                if (startDateSelection != null) {
                    calendarData[startDateSelection!!.position] =
                        startDateSelection!!.day.copy(selection = SelectionType.NONE)
                }
                assignAsStartDate(item, position)
            }
            startDateSelection == null -> assignAsStartDate(item, position)
            endDateSelection == null -> {
                if (startDateSelection!!.position > position) {
                    calendarData[startDateSelection!!.position] =
                        startDateSelection!!.day.copy(selection = SelectionType.NONE)
                    assignAsStartDate(item, position)
                } else {
                    assignAsStartDate(
                        startDateSelection!!.day,
                        startDateSelection!!.position,
                        true
                    )
                    assignAsEndDate(item, position)
                    highlightDateBetween(startDateSelection!!.position, position)
                }
            }
            else -> {
                resetSelection()
                assignAsStartDate(item, position)
            }
        }
        calendarAdapter.submitList(calendarData)
    }

    // Range를 바꾸는 경우엔 기존에 선택되어 있는 녀석들을 초기화 해주어야 한다.
    private fun resetSelection() {
        val startDatePosition = startDateSelection?.position
        val endDatePosition = endDateSelection?.position

        if (startDatePosition != null && endDatePosition != null) {
            (startDatePosition..endDatePosition).forEach {
                val entity = calendarData[it]
                if (entity is CalendarEntity.Day)
                    calendarData[it] = entity.copy(selection = SelectionType.NONE)
            }
        }
        endDateSelection = null
    }

    // 선택되어 있는 녀석들을 기준으로 끼어있는 녀석들은 전부 선택됐음을 표기해주는 함수
    private fun highlightDateBetween(
        startIndex: Int,
        endIndex: Int
    ) {
        ((startIndex + 1) until endIndex).forEach {
            val entity = calendarData[it]
            if (entity is CalendarEntity.Day) {
                calendarData[it] = entity.copy(selection = SelectionType.BETWEEN)
            }
        }
    }

    private fun assignAsStartDate(
        item: CalendarEntity.Day,
        position: Int,
        isRange: Boolean = false
    ) {
        val newItem = item.copy(selection = SelectionType.START, isRange = isRange)
        calendarData[position] = newItem
        startDateSelection = SelectedDate(newItem, position)
        if (!isRange) onStartSelectedListener.invoke(item.date, item.prettyLabel)
    }

    private fun assignAsEndDate(
        item: CalendarEntity.Day,
        position: Int
    ) {
        val newItem = item.copy(selection = SelectionType.END)
        calendarData[position] = newItem
        endDateSelection = SelectedDate(newItem, position)
        onRangeSelectedListener.invoke(
            startDateSelection!!.day.date,
            item.date,
            startDateSelection!!.day.prettyLabel,
            item.prettyLabel
        )
    }

    data class SelectedDate(val day: CalendarEntity.Day, val position: Int)
}