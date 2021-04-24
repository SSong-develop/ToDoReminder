package com.hks.kr.wifireminder.ui.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.databinding.ItemTaskBinding
import com.hks.kr.wifireminder.domain.entity.TaskEntity
import com.hks.kr.wifireminder.utils.shortToast

val diffItemCallback = object : DiffUtil.ItemCallback<TaskEntity>() {
    override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean =
        oldItem.taskId == newItem.taskId

    override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean =
        oldItem.taskId == newItem.taskId

}

class HomeTaskAdapter : ListAdapter<TaskEntity, HomeTaskAdapter.TaskViewHolder>(diffItemCallback) {
    private val items = mutableListOf<TaskEntity>()

    class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TaskEntity) {
            binding.task = item
            binding.root.apply {
                setOnClickListener {
                    it.context.shortToast("clicked")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemTaskBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_task, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

@BindingAdapter("task_adapter_items")
fun RecyclerView.setItems(items: List<TaskEntity>) {
    (adapter as? HomeTaskAdapter)?.run {
        submitList(items)
    }
}