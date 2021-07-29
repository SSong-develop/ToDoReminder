package com.hks.kr.wifireminder.view.calendarPicker.entity

import java.util.*

sealed class CalendarEntity(
    val columnCount: Int, // 각 날이 몇개의 박스?를 가지고 있어야 하는지
    val calendarType: Int, // 달인지, 주말인지 ,날인지에 대한 type
    val selectionType: SelectionType // range로 클릭할 것 인지, Single로 클릭을 할 것인지
) {
    /**
     * 달
     */
    data class Month(val label: String, val year: String) :
        CalendarEntity(MONTH_COLUMN_COUNT, CalendarType.MONTH.ordinal, SelectionType.NONE)

    /**
     * 주말
     */
    object Week : CalendarEntity(WEEK_COLUMN_COUNT, CalendarType.WEEK.ordinal, SelectionType.NONE)

    /**
     * 날
     */
    data class Day(
        val label: String,
        val prettyLabel: String,
        val date: Date,
        val selection: SelectionType = SelectionType.NONE,
        val state: DateState = DateState.WEEKDAY,
        val isRange: Boolean = false
    ) : CalendarEntity(DAY_COLUMN_COUNT, CalendarType.DAY.ordinal, selection)

    /**
     * 비어있는 경우, 달력이 월요일부터 시작하지 않기 때문에 , 이전에 날들은 비어있어야 한다.
     */
    object Empty :
        CalendarEntity(EMPTY_COLUMN_COUNT, CalendarType.EMPTY.ordinal, SelectionType.NONE)
}

const val TOTAL_COLUMN_COUNT = 7
const val MONTH_COLUMN_COUNT = 7
const val WEEK_COLUMN_COUNT = 7
const val DAY_COLUMN_COUNT = 1
const val EMPTY_COLUMN_COUNT = 1

// 달력에 들어갈 종류
// 1월 , 2월 , 3월 ....을 나타내줄 MONTH
// 한 주를 나타내줄 WEEK
// 하루를 나타내줄 DAY
// 비어있는 경우 EMPTY
enum class CalendarType {
    MONTH,
    WEEK,
    DAY,
    EMPTY
}

// 달력을 선택할 때 , 어떤 종류의 선택인지
// 시작 날을 알려주는 START
// 끝나는 날을 알려주는 END
// 시작과 끝 사이에 있는 BETWEEN
// 이미 날이 지나 , 날짜를 선택할 수 없는 경우 Ex) 7월 10일인데 7월 6일부터 시작하고 싶을 경우 이는 불가능하다.
enum class SelectionType {
    START,
    BETWEEN,
    END,
    NONE
}

// 달력의 1일 , 2일 , 3일이 어떤 상태인지를 알려준다.
// 평일인 경우 WEEKDAY
// 이미 지난 날은 DISABLED
// 주말인 경우 WEEKEND
enum class DateState {
    WEEKDAY,
    DISABLED,
    WEEKEND
}

// 달력의 선택 모드를 결정해준다.
// 범위 선택이 가능한 RANGE
// 단일 선택이 가능한 SINGLE
enum class SelectionMode {
    RANGE,
    SINGLE
}