package com.hks.kr.wifireminder.view.calendar.model

/**
 * Describes the month to which a [CalendarDay] belongs.
 *
 * CalendarDay 의 달을 표현하는 enum
 */

enum class DayOwner {
    /**
     * Belongs to the previous month on the calendar
     * Such days are referred to as inDates.
     *
     * 이전 달에 날짜가 있어야하는 경우 , 한 주마다 7개씩 둘건데 달 중에 이전 달의 몇개의 칸을 가져가는 달이 있을 경우 이를 PREVIOUS_MONTH로 표현
     * inDates라고 이름 지어줌
     */
    PREVIOUS_MONTH,

    /**
     * Belongs to the current month on the calendar
     * Such days are referred to as monthDates.
     *
     * 최근 달에 속하는 경우
     * monthDates라고 이름 지어줌줌
    */
    THIS_MONTH,

    /**
     * Belongs to the next month on the calendar
     * Such days are referred to as outDates.
     *
     * 그 다음 달에 속하는 경우
     * outDates라고 이름 지음
     */
    NEXT_MONTH
}

/**
 * Determines how outDates are generated for each month on the calendar
 *
 * outDates가 캘린더의 각 달에 어떻게 구성되어야 하는지를 결정
 */
enum class OutDateStyle {
    /**
     * The calendar will generate outDates until it reaches
     * the first end of a row. this means that if a month
     * has 6 rows, it will display 6 rows and if a month
     * has 5 rows, it will display 5 rows.
     */
    END_OF_ROW,

    /**
     * The calendar will generate outDates until
     * it reaches the end of a 6*7 grid.
     * This means that all months will have 6 rows.
     */
    END_OF_GRID,

    /**
     * outDates will not be generated
     */
    NONE
}

/**
 * Determines how inDates are generated for
 * each month on the calendar
 */
enum class InDateStyle {
    /**
     * inDates will be generated for all months.
     */
    ALL_MONTHS,

    /**
     * inDates will be generated for the first month only.
     */
    FIRST_MONTH,

    /**
     * inDates will not be generated, this means that there
     * will be no offset on any month.
     */
    NONE
}

/**
 * The scrolling behavior of the calendar
 */
enum class ScrollMode {
    /**
     * The calendar will snap to the nearset month
     * after a scroll or swipe action.
     */
    CONTINUOUS,

    /**
     * The calendar scrolls normally
     */
    PAGED
}