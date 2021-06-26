package com.hks.kr.wifireminder.view.calendar.ui

import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.core.view.*
import com.hks.kr.wifireminder.view.calendar.extension.inflate
import com.hks.kr.wifireminder.view.calendar.model.CalendarDay
import com.hks.kr.wifireminder.view.calendar.util.Size

internal data class DayConfig(
    val size: Size,
    @LayoutRes val dayViewRes: Int,
    val viewBinder: DayBinder<ViewContainer>
)

internal class DayHolder(private val config: DayConfig) {

    private lateinit var dateView: View
    private lateinit var viewContainer: ViewContainer
    private var day: CalendarDay? = null

    fun inflateDayView(parent: LinearLayout): View {
        dateView = parent.inflate(config.dayViewRes).apply {
            // This will be placed in the WeekLayout(A LinearLayout) hence we
            // use LinearLayout.LayoutParams and set the weight appropriately
            // The parent's wightSum is already set to 7 to accomodate seven week days.
            updateLayoutParams<LinearLayout.LayoutParams> {
                width = config.size.width - marginStart - marginEnd
                height = config.size.height - marginTop - marginBottom
                weight = 1f
            }
        }
        return dateView
    }

    fun bindDayView(currentDay: CalendarDay?) {
        this.day = currentDay
        if (!::viewContainer.isInitialized) {
            viewContainer = config.viewBinder.create(dateView)
        }

        val dayHash = currentDay?.date.hashCode()
        if (viewContainer.view.tag != dayHash) {
            viewContainer.view.tag = dayHash
        }

        if (currentDay != null) {
            if (!viewContainer.view.isVisible) {
                viewContainer.view.isVisible = true
            }
            config.viewBinder.bind(viewContainer, currentDay)
        } else if (!viewContainer.view.isGone) {
            viewContainer.view.isGone = true
        }
    }

    fun reloadViewIfNessary(day: CalendarDay): Boolean {
        return if(day == this.day) {
            bindDayView(this.day)
            true
        } else {
            false
        }
    }
}