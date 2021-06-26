package com.hks.kr.wifireminder.view.calendar

import androidx.recyclerview.widget.RecyclerView
import com.hks.kr.wifireminder.view.calendar.model.ScrollMode
import com.hks.kr.wifireminder.view.calendar.ui.DayBinder
import com.hks.kr.wifireminder.view.calendar.ui.MonthHeaderFooterBinder
import com.hks.kr.wifireminder.view.calendar.ui.MonthScrollListener

typealias Completion = () -> Unit

open class CalendarView : RecyclerView {

    /**
     * The [DayBinder] instance used for managing day cell views
     * creation and reuse. Changing the binder means that the view
     * creation logic could have changed too. We refresh the Calendar
     */
    var dayBinder: DayBinder<*>? = null
        set(value) {
            field = value
            invalidateViewHolders()
        }

    /**
     * The [MonthHeaderFooterBinder] instance used for managing header views.
     * The Header view is shown above each month on the Calendar.
     */
    var monthHeaderBinder: MonthHeaderFooterBinder<*>? = null
        set(value) {
            field = value
            invalidateViewHolders()
        }

    /**
     * Call when the calendar scrolls to a new month. Mostly beneficial
     * if [ScrollMode] is [ScrollMode.PAGED]
     */
    var monthScrollListener: MonthScrollListener? = null

    /**
     * The xml resource that is inflated and used as the day cell view.
     * This must be provided.
     */
    var dayViewResource = 0
        set(value) {
            if (field != value) {
                if (value == 0) throw IllegalArgumentException("'dayViewResource' attribute not provide.")
                field = value
                updateAdapterViewConfig()
            }
        }

    /**
     * The xml resource that is inflated and used as a footer for every month.
     * Set zero to disable
     */
    var monthFooterResource = 0
        set(value) {
            if (field != value) {
                field = value
                updateAdapterViewConfig()
            }
        }

    /**
     * A [ViewGroup] which is instantiated and used as the background for each month.
     * This class must have a constructor which takes only a [Context]. You should
     * exclude the name and constructor of this class from code obfuscation if enabled/
     */
    var monthViewClass: String? = null
        set(value) {
            if (field != value) {
                field = value
                updateAdapterViewConfig()
            }
        }

    /**
     * The [RecyclerView.Orientation] used for the layout manager
     * This determines the scroll direction of the calendar
     */
    @Orientation
    var orientation = VERTICAL
        set(value) {
            if (field != value) {
                field = value
                setup(startMonth ?: return, endMonth ?: return, firstDayOfWeek ?: return)
            }
        }

    /**
     * The scrolling behavior of the calendar. If [ScrollMode.PAGED],
     * the calendar will snap to the nearest month after a scroll or swipe action.
     * If [ScrollMode.CONTINUOUS], the calendar scrolls normally
     */
    var scrollMode = ScrollMode.CONTINUOUS
        set(value) {
            if (field != value) {
                field = value
                pagerSnapHelper.attachToRecyclerView(if (value == ScrollMode.PAGED) this else null)
            }
        }


}