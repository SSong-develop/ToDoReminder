package com.hks.kr.wifireminder.ui.common.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hks.kr.wifireminder.databinding.ItemTaskBinding
import com.hks.kr.wifireminder.domain.entity.Task

class TaskViewHolder(
    private val binding: ItemTaskBinding,
    private val delegate: Delegate
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var task: Task

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    interface Delegate {
        fun onTaskItemClick(view: View, task: Task)
    }

    fun bind(data: Task) {
        task = data
        binding.apply {
            task = data
            executePendingBindings()
        }
    }

    override fun onClick(view: View) {
        delegate.onTaskItemClick(view, task)
    }

    override fun onLongClick(v: View?): Boolean = false
}