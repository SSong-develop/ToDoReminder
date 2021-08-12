package com.hks.kr.wifireminder.view.calendarPicker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.view.calendarPicker.entity.CalendarEntity
import com.hks.kr.wifireminder.view.calendarPicker.entity.CalendarType
import com.hks.kr.wifireminder.view.calendarPicker.util.CalendarDiffCallback
import com.hks.kr.wifireminder.view.calendarPicker.viewholder.BaseCalendarViewHolder
import com.hks.kr.wifireminder.view.calendarPicker.viewholder.DayViewHolder
import com.hks.kr.wifireminder.view.calendarPicker.viewholder.EmptyViewHolder
import com.hks.kr.wifireminder.view.calendarPicker.viewholder.MonthViewHolder

class CalendarAdapter :
    ListAdapter<CalendarEntity, BaseCalendarViewHolder>(CalendarDiffCallback()) {

    var onActionListener: (CalendarEntity, Int) -> Unit = { _, _ -> }

    override fun submitList(list: MutableList<CalendarEntity>?) {
        super.submitList(list?.toMutableList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseCalendarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CalendarType.MONTH.ordinal -> {
                MonthViewHolder(layoutInflater.inflate(R.layout.view_calendar_month, parent, false))
            }
            CalendarType.DAY.ordinal -> {
                DayViewHolder(layoutInflater.inflate(R.layout.view_calendar_day, parent, false))
            }
            else -> {
                EmptyViewHolder(layoutInflater.inflate(R.layout.view_calendar_empty, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: BaseCalendarViewHolder, position: Int) {
        holder.onBind(getItem(position), onActionListener)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).calendarType
    }
}