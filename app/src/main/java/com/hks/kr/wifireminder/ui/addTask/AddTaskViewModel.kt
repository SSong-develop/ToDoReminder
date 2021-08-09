package com.hks.kr.wifireminder.ui.addTask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hks.kr.wifireminder.data.source.TasksRepository
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskRepository: TasksRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val categoryTitle = MutableLiveData<String>()

    val importance = MutableLiveData<Int>()

    private val _categoryImportance = mutableListOf<String>("High", "Medium", "Low")
    val categoryImportance: List<String>
        get() = _categoryImportance

    private val categoryFlow: Flow<List<Category>> =
        categoryRepository.observeCategories()

    private val _categoryItems = MutableLiveData<List<Category>>()
    val categoryItems: LiveData<List<Category>>
        get() = _categoryItems

    init {
        viewModelScope.launch {
            categoryFlow.onStart {

            }.onCompletion {

            }.catch {

            }.collect {
                _categoryItems.value = it
            }
        }
    }
}