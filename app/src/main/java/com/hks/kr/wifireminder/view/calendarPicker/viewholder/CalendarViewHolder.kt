package com.hks.kr.wifireminder.view.calendarPicker.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.view.calendarPicker.entity.CalendarEntity

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
        }
    }

}
class CalendarViewHolder {
}