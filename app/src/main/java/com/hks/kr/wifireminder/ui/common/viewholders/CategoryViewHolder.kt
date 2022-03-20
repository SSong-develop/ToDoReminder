package com.hks.kr.wifireminder.ui.common.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hks.kr.wifireminder.databinding.ItemTaskCategoryBinding
import com.hks.kr.wifireminder.domain.entity.Category

class CategoryViewHolder(
    private val binding : ItemTaskCategoryBinding,
    private val delegate : Delegate
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var category : Category

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    interface Delegate {
        fun onCategoryItemClick(view : View, category: Category)
    }

    fun bind(data : Category) {
        category = data
        binding.apply {
            category = data
            executePendingBindings()
        }
    }

    override fun onClick(view: View) {
        delegate.onCategoryItemClick(view,category)
    }

    override fun onLongClick(v: View?): Boolean = false
}