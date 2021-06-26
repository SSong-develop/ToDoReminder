package com.hks.kr.wifireminder.view.calendar.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

internal class MonthViewHolder constructor(
    adapter : CalendarAdapter,
    rootLayout : ViewGroup,
    private val weekHolders : List<WeekHolder>,
    private var monthHeaderBinder : MonthHeaderFooterBinder<ViewContainer>?,
    private var monthFooterBinder : MonthHeaderFooterBinder<ViewContainer>?
) : RecyclerView.ViewHolder(rootLayout){

    val headerView : View? = rootLayout.findViewById(adapter.header)
}