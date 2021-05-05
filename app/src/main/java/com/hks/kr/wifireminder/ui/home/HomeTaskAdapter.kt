package com.hks.kr.wifireminder.ui.home

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

/**
 * ConcatAdapter를 사용해서 한번 해볼까요???
 *
 */
val diffItemCallback = object : DiffUtil.ItemCallback<TaskEntity>() {
    override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean =
        oldItem.taskId == newItem.taskId

    override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean =
        oldItem.taskId == newItem.taskId

}

class HomeTaskAdapter(
    private val onItemClicked: (idx: Int, item: TaskEntity) -> Unit
) : ListAdapter<TaskEntity, HomeTaskAdapter.TaskViewHolder>(diffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemTaskBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_task, parent, false)
        return TaskViewHolder(binding)
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.onClick = {
                onItemClicked(layoutPosition, getItem(layoutPosition))
            }
        }

        fun bind(item: TaskEntity) {
            binding.task = item
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

@BindingAdapter("task_adapter_item")
fun RecyclerView.setItems(items: List<TaskEntity>) {
    (adapter as? HomeTaskAdapter)?.run {
        submitList(items)
    }
}