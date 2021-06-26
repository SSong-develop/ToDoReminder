package com.hks.kr.wifireminder.view.calendar.ui

import android.view.ViewGroup
import android.widget.CalendarView
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.hks.kr.wifireminder.view.calendar.model.CalendarMonth
import com.hks.kr.wifireminder.view.calendar.model.MonthConfig

internal typealias LP = ViewGroup.LayoutParams

internal data class ViewConfig(
    @LayoutRes val dayViewRes: Int,
    @LayoutRes val monthHeaderRes: Int,
    @LayoutRes val monthFooterRes: Int,
    val monthViewClass: String?
)

internal class CalendarAdapter(
    private val calView: CalendarView,
    internal var viewConfig: ViewConfig,
    internal var monthConfig: MonthConfig
) : RecyclerView.Adapter<MonthViewHolder>() {

    private val months: List<CalendarMonth>
        get() = monthConfig.months

    // Values of headerViewId & footerViewId will be
    // replaced with IDs set in the XML if present.
    var headerViewId = ViewCompat.generateViewId()
    var footerViewId = ViewCompat.generateViewId()

    init {
        setHasStableIds(true)
        registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                initialLayout = true
            }
        })
    }

    private val isAttached: Boolean
        get() = calView.adapter === this

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}