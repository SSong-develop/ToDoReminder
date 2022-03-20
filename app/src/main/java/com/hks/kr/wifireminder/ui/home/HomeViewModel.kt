package com.hks.kr.wifireminder.ui.home

import androidx.lifecycle.*
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.domain.entity.Task
import com.hks.kr.wifireminder.domain.repository.CategoryRepository
import com.hks.kr.wifireminder.domain.repository.TasksRepository
import com.hks.kr.wifireminder.utils.Event
import com.hks.kr.wifireminder.vo.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TasksRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    // TODO : Naming 다시 생각
    sealed class UiState {
        object Loading : UiState()
        object Active : UiState()
        object InActive : UiState()
    }

    /** UiState Flow **/
    private val _state: StateFlow<UiState> = flow {
        combine(_taskStateFlow, _categoryStateFlow) { taskState, categoryState ->
            if (taskState is Result.Success && categoryState is Result.Success) {
                emit(UiState.Active)
            } else {
                emit(UiState.InActive)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), UiState.Loading)
    val state: StateFlow<UiState>
        get() = _state

    /** taskFlow **/
    private val _taskStateFlow: MutableStateFlow<Result<List<Task>>> = MutableStateFlow(Result.Loading)
    val taskStateFlow: StateFlow<Result<List<Task>>>
        get() = _taskStateFlow

    /** categoryFlow **/
    private val _categoryStateFlow: MutableStateFlow<Result<List<Category>>> = MutableStateFlow(Result.Loading)
    val categoryStateFlow: StateFlow<Result<List<Category>>>
        get() = _categoryStateFlow


    private val _taskItems = MutableLiveData<List<Task>>(listOf())
    val taskItems: LiveData<List<Task>>
        get() = _taskItems

    private val _categoryItems = MutableLiveData<List<Category>>(listOf())
    val categoryItems: LiveData<List<Category>>
        get() = _categoryItems


    /**
     * SnackBar Event LiveData
     */
    private val _snackBarText = MutableLiveData<Event<Int>>()
    val snackBarText: LiveData<Event<Int>>
        get() = _snackBarText

    /**
     * TODO : 로직 변경해야함
     */
    /**
     * Task Value State LiveData
     */
    private val _isExistTask = MutableLiveData<Boolean>()
    val isExistTask: LiveData<Boolean> = _isExistTask

    val empty: LiveData<Boolean> = Transformations.map(_taskItems) {
        it.isEmpty()
    }

    /**
     * taskDataLoading UI State LiveData
     */
    private val _taskDataLoading = MutableLiveData<Boolean>()
    val taskDataLoading: LiveData<Boolean> = _taskDataLoading

    /**
     * CategoryDataLoading UI State LiveData
     */
    private val _categoryDataLoading = MutableLiveData<Boolean>()
    val categoryDataLoading: LiveData<Boolean> = _categoryDataLoading

    private fun showSnackBarMessage(message: Int) {
        _snackBarText.value = Event(message)
    }

    fun getTasksByCategory(categoryTitle: String) = viewModelScope.launch {
        runCatching {
            taskRepository.getTaskByCategory(categoryTitle)
        }.onSuccess { result ->
            if (result is Result.Success) {
                _taskItems.value = result.data!!
            } else {
                showSnackBarMessage(R.string.loading_tasks_error)
            }
        }.onFailure {
            showSnackBarMessage(R.string.loading_tasks_error)
        }
    }

    fun fetchTasksByCategory(categoryTitle: String) = viewModelScope.launch {
        runCatching {
            taskRepository.getTaskByCategory(categoryTitle)
        }.onSuccess { result ->
            when (result) {
                is Result.Success -> {
                    _taskStateFlow.value = result
                }
                is Result.Loading -> {
                    // TODO: Need to check Loading State
                }
                is Result.Error -> {
                    _taskStateFlow.value = Result.Error(Exception("Fetch Data Error"))
                }
            }
        }.onFailure {
            showSnackBarMessage(R.string.loading_tasks_error)
        }
    }

    companion object {
        private const val TASK_LIST_SCROLL_POSITION = "TASK_LIST_SCROLL_POSITION"
        private const val CATEGORY_LIST_SCROLL_POSITION = "CATEGORY_LIST_SCROLL_POSITION"
    }
}