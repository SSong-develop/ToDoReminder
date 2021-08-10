package com.hks.kr.wifireminder.ui.addTask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hks.kr.wifireminder.data.TaskDTO
import com.hks.kr.wifireminder.data.source.TasksRepository
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.domain.repository.CategoryRepository
import com.hks.kr.wifireminder.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskRepository: TasksRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    // Two-way DataBinding
    val title = MutableLiveData<String>()

    // Two-way DataBinding
    val description = MutableLiveData<String>()

    val date = MutableLiveData<Pair<String, String>>()

    // spinner
    val categoryTitle = MutableLiveData<String>()

    // spinner
    val importance = MutableLiveData<String>()

    private val _categoryImportance = mutableListOf<String>("High", "Medium", "Low")
    val categoryImportance: List<String>
        get() = _categoryImportance

    private val categoryFlow: Flow<List<Category>> =
        categoryRepository.observeCategories()

    private val _categoryItems = MutableLiveData<List<Category>>()
    val categoryItems: LiveData<List<Category>>
        get() = _categoryItems

    private val _snackBarText = MutableLiveData<Event<Int>>()
    val snackBarText: LiveData<Event<Int>>
        get() = _snackBarText


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

    fun saveTask() =
        viewModelScope.launch {
            taskRepository.saveTask(
                TaskDTO(
                    title = title.value.toString(),
                    description = description.value.toString(),
                    startDate = date.value!!.first,
                    endDate = date.value!!.second,
                    category = categoryTitle.value.toString(),
                    importance = importance.value.toString()
                )
            )
        }


    fun showSnackBarMessage(message: Int) {
        _snackBarText.value = Event(message)
    }

    fun isValidate(): Boolean {
        return !title.value.isNullOrEmpty() && !description.value.isNullOrEmpty() && !date.value?.first.isNullOrEmpty() && !date.value?.second.isNullOrEmpty()
    }

}