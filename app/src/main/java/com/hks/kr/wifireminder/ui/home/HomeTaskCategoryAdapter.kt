package com.hks.kr.wifireminder.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.hks.kr.wifireminder.databinding.ItemTaskCategoryBinding
import com.hks.kr.wifireminder.domain.entity.Category

class HomeTaskCategoryAdapter(
    private val onItemClicked: (idx: Int, item: Category) -> Unit
) : Adapter<HomeTaskCategoryAdapter.CategoryViewHolder>() {
    private val items = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskCategoryBinding.inflate(inflater, parent, false)

        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class CategoryViewHolder(private val binding: ItemTaskCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.onClick = {
                onItemClicked(layoutPosition, items[layoutPosition])
            }
        }

        fun bind(item: Category) {
            binding.items = item
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(itemList: List<Category>) {
        items.clear()
        items.addAll(itemList)
        notifyDataSetChanged()
    }

}

@BindingAdapter("task_category_items")
fun RecyclerView.setItems(items: List<Category>) {
    (adapter as? HomeTaskCategoryAdapter)?.run {
        submitList(items)
    }
}
