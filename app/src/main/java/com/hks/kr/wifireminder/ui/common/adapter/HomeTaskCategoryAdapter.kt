package com.hks.kr.wifireminder.ui.common.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.hks.kr.wifireminder.databinding.ItemTaskCategoryBinding
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.ui.common.viewholders.CategoryViewHolder
import com.hks.kr.wifireminder.vo.Result

class HomeTaskCategoryAdapter(
    private val delegate : CategoryViewHolder.Delegate
) : Adapter<CategoryViewHolder>() {
    private val items = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskCategoryBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding,delegate)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(itemList: List<Category>) {
        items.clear()
        items.addAll(itemList)
        notifyDataSetChanged()
    }
}

@BindingAdapter("task_category_items")
fun RecyclerView.setItems(items: Result<List<Category>>) {
    val homeTaskCategoryAdapter = adapter as? HomeTaskCategoryAdapter
    when(items) {
        is Result.Success -> {
            homeTaskCategoryAdapter?.run {
                submitList(items.data)
            }
        }
        is Result.Loading -> {
            // TODO : 잘 처리하세요 송훈기 씨
        }
        is Result.Error -> {
            // TODO : 잘 처리하세요 송훈기 씨
        }
    }
}
