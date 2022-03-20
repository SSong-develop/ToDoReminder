package com.hks.kr.wifireminder.ui.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.databinding.ItemTaskBinding
import com.hks.kr.wifireminder.domain.entity.Task
import com.hks.kr.wifireminder.ui.common.viewholders.TaskViewHolder
import com.hks.kr.wifireminder.vo.Result

val diffItemCallback = object : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem.id == newItem.id

}

class HomeTaskAdapter(
    private val delegate : TaskViewHolder.Delegate
) : ListAdapter<Task, TaskViewHolder>(diffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemTaskBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_task, parent, false)
        return TaskViewHolder(binding,delegate)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

@BindingAdapter("task_adapter_item")
fun RecyclerView.setItems(items: Result<List<Task>>) {
    val homeTaskAdapter = adapter as? HomeTaskAdapter
    when(items) {
        is Result.Success -> {
            homeTaskAdapter?.run { submitList(items.data) }
        }
        is Result.Loading -> { // TODO : 잘 처리하세요 송훈기 씨
        }
        is Result.Error -> { // TODO : 잘 처리하세요 송훈기 씨
        }
    }
}