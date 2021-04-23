package com.hks.kr.wifireminder.ui.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hks.kr.wifireminder.domain.entity.TaskEntity

val diffItemCallback = object : DiffUtil.ItemCallback<TaskEntity>() {
    override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean = oldItem.taskId == newItem.taskId

    override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
        TODO("Not yet implemented")
    }

}

class HomeTaskAdapter {
}