package com.hks.kr.wifireminder.view.calendarPicker.viewholder

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.view.calendarPicker.entity.CalendarEntity
import com.hks.kr.wifireminder.view.calendarPicker.entity.DateState
import com.hks.kr.wifireminder.view.calendarPicker.entity.SelectionType

abstract class BaseCalendarViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(item : CalendarEntity , actionListener : (CalendarEntity,Int) -> Unit)
}

class MonthViewHolder(private val view : View) : BaseCalendarViewHolder(view) {

    private val name by lazy { view.findViewById<TextView>(R.id.tv_month_name) }
    override fun onBind(item: CalendarEntity, actionListener: (CalendarEntity, Int) -> Unit) {
        if(item is CalendarEntity.Month){
            name.text = item.label
        }
    }
}

class DayViewHolder(private val view : View) : BaseCalendarViewHolder(view){

    private val name by lazy { view.findViewById<TextView>(R.id.tv_day_name) }
    private val halfLeftBg by lazy { view.findViewById<View>(R.id.vHalfLeftBg) }
    private val halfRightBg by lazy { view.findViewById<View>(R.id.vHalfRightBg) }

    override fun onBind(item: CalendarEntity, actionListener: (CalendarEntity, Int) -> Unit) {
        if(item is CalendarEntity.Day){
            name.text = item.label
            when(item.selection) {
                SelectionType.START -> {

                }
                SelectionType.END -> {

                }
                SelectionType.BETWEEN -> {

                }
                SelectionType.NONE -> {

                }
            }
        }
    }

    private fun getFontColor(item : CalendarEntity.Day) : Int {
        return if(item.selection == SelectionType.START || item.selection == SelectionType.END) {
            ContextCompat.getColor(itemView.context,R.color.calendar_day_selected_font_color)
        } else if(item.selection == SelectionType.BETWEEN && item.state != DateState.WEEKEND) {
            ContextCompat.getColor(itemView.context,R.color.teal_200)
        } else {
            val color = when(item.state){
                DateState.DISABLED -> R.color.gray
                DateState.WEEKEND -> R.color.red
                else -> R.color.black
            }
            ContextCompat.getColor(itemView.context,color)
        }
    }

    private fun View.select() {
        val drawable = ContextCompat.getDrawable(context,R.drawable.bg_calendar_select_day)
        background = drawable
    }

    private fun View.deSelect() {
        background = null
    }

    private fun View.dehighlight() {
        val color = ContextCompat.getColor(context,R.color.calendar_day_unselected_bg_color)
        setBackgroundColor(color)
    }

    private fun View.highlight() {

    }
}
class CalendarViewHolder {
}