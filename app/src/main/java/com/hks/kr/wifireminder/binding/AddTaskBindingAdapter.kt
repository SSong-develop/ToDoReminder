package com.hks.kr.wifireminder.binding

import androidx.databinding.BindingAdapter
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.ui.addTask.AddTaskViewModel
import com.hks.kr.wifireminder.utils.debugE
import com.hks.kr.wifireminder.view.customizableSpinner.CustomizableSpinner

object AddTaskBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:title_spinner_item")
    fun setSpinnerItem(customSpinner: CustomizableSpinner, item: List<Category>?) {
        val itemArray = item?.let { Array<String>(it.size) { _ -> "" } }
        if (item != null) {
            for (i in item.indices) {
                itemArray?.set(i, item[i].categoryName)
            }
        }
        customSpinner.apply {
            if (itemArray != null) {
                dataSet = itemArray
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:onSelectChanged")
    fun onSelectChanged(customSpinner: CustomizableSpinner, viewModel: AddTaskViewModel) {
        customSpinner.selectionChanged { _, s ->
            viewModel.categoryTitle.value = s
            debugE(viewModel.categoryTitle.value)
        }
    }

    @JvmStatic
    @BindingAdapter("app:importance_spinner_item")
    fun setImportanceItem(customSpinner: CustomizableSpinner, item: List<String>) {
        val itemArray = item.let {
            Array<String>(it.size) { _ -> "" }
        }
        for(i in item.indices){
            itemArray[i] = item[i]
        }
        customSpinner.apply {
            dataSet = itemArray
        }
    }

    @JvmStatic
    @BindingAdapter("app:importanceSelectChanged")
    fun importanceSelectChanged(customSpinner: CustomizableSpinner,viewModel: AddTaskViewModel){
        customSpinner.selectionChanged { i, s ->
            viewModel.importance.value = i
            debugE(viewModel.importance.value)
        }
    }

}