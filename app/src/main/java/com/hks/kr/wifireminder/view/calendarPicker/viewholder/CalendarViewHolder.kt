package com.hks.kr.wifireminder.view.calendarPicker.viewholder

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.view.calendarPicker.entity.CalendarEntity
import com.hks.kr.wifireminder.view.calendarPicker.entity.DateState
import com.hks.kr.wifireminder.view.calendarPicker.entity.SelectionType

abstract class BaseCalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(item: CalendarEntity, actionListener: (CalendarEntity, Int) -> Unit)
}

/**
 * 년도와 월을 표시하는 monthViewHolder
 */
class MonthViewHolder(private val view: View) : BaseCalendarViewHolder(view) {

    private val name by lazy { view.findViewById<TextView>(R.id.tv_month_name) }
    override fun onBind(item: CalendarEntity, actionListener: (CalendarEntity, Int) -> Unit) {
        if (item is CalendarEntity.Month) {
            name.text = item.label
        }
    }
}

/**
 * 달력의 각 월별 일을 표시하는 DayViewHolder
 */
class DayViewHolder(private val view: View) : BaseCalendarViewHolder(view) {

    private val name by lazy { view.findViewById<TextView>(R.id.tv_day_name) }
    private val halfLeftBg by lazy { view.findViewById<View>(R.id.vHalfLeftBg) }
    private val halfRightBg by lazy { view.findViewById<View>(R.id.vHalfRightBg) }

    override fun onBind(item: CalendarEntity, actionListener: (CalendarEntity, Int) -> Unit) {
        if (item is CalendarEntity.Day) {
            name.text = item.label
            when (item.selection) {
                SelectionType.START -> {
                    name.select()
                    halfLeftBg.dehighlight()
                    if (item.isRange) halfRightBg.highlight()
                    else halfRightBg.dehighlight()
                }
                SelectionType.END -> {
                    name.select()
                    halfLeftBg.highlight()
                    halfRightBg.dehighlight()
                }
                SelectionType.BETWEEN -> {
                    name.deSelect()
                    halfRightBg.highlight()
                    halfLeftBg.highlight()
                }
                SelectionType.NONE -> {
                    halfLeftBg.dehighlight()
                    halfRightBg.dehighlight()
                    name.deSelect()
                }
            }

            name.setTextColor(getFontColor(item))
            if (item.state != DateState.DISABLED) {
                itemView.setOnClickListener {
                    actionListener.invoke(
                        item,
                        adapterPosition
                    )
                }
            } else {
                itemView.setOnClickListener(null)
            }
        }
    }

    private fun getFontColor(item: CalendarEntity.Day): Int {
        return if (item.selection == SelectionType.START || item.selection == SelectionType.END) {
            ContextCompat.getColor(itemView.context, R.color.calendar_day_selected_font_color)
        } else if (item.selection == SelectionType.BETWEEN && item.state != DateState.WEEKEND) {
            ContextCompat.getColor(itemView.context, R.color.teal_200)
        } else {
            val color = when (item.state) {
                DateState.DISABLED -> R.color.gray
                DateState.WEEKEND -> R.color.red
                else -> R.color.black
            }
            ContextCompat.getColor(itemView.context, color)
        }
    }

    /**
     * 일자가 선택되었을 경우 , 원형 drawable이 나오도록 한다.
     */
    private fun View.select() {
        val drawable = ContextCompat.getDrawable(context, R.drawable.bg_calendar_select_day)
        background = drawable
    }

    /**
     * 일자가 선택되어 있지 않을 경우
     */
    private fun View.deSelect() {
        background = null
    }

    /**
     * Range로 클릭할 때 주변 색상을 그대로 돌려놓는 함수
     */
    private fun View.dehighlight() {
        val color = ContextCompat.getColor(context, R.color.calendar_day_unselected_bg_color)
        setBackgroundColor(color)
    }

    /**
     * 범위 클릭 시 껴있는 날들을 전부 색칠하는 함수
     */
    private fun View.highlight() {
        val color = ContextCompat.getColor(context, R.color.calendar_day_highlight_bg_color)
        setBackgroundColor(color)
    }
}

class EmptyViewHolder(view: View) : BaseCalendarViewHolder(view) {
    override fun onBind(item: CalendarEntity, actionListener: (CalendarEntity, Int) -> Unit) {}
}