package com.hks.kr.wifireminder.ui.home

import androidx.lifecycle.*
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.data.Result
import com.hks.kr.wifireminder.domain.repository.TasksRepository
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.domain.entity.Task
import com.hks.kr.wifireminder.domain.repository.CategoryRepository
import com.hks.kr.wifireminder.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TasksRepository,
    private val categoryRepository: CategoryRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * TODO : 여기 로직 부분을 변경해야합니다.
     *
     * TODO : LiveData를 전부 Flow로 마이그레이션 합니다!
     */
    private val _taskFlow: Flow<List<Task>> =
        taskRepository.observeTasks()

    private val _categoryFlow: Flow<List<Category>> =
        categoryRepository.observeCategories()

    private val _taskItems = MutableLiveData<List<Task>>(listOf())
    val taskItems: LiveData<List<Task>>
        get() = _taskItems

    private val _categoryItems = MutableLiveData<List<Category>>(listOf())
    val categoryItems: LiveData<List<Category>>
        get() = _categoryItems

    /**
     * TaskList Scroll Position
     */
    val taskListPosition: Int?
        get() = savedStateHandle.get<Int>(TASK_LIST_SCROLL_POSITION)

    /**
     * CategoryList Scroll Position
     */
    val categoryListPosition: Int?
        get() = savedStateHandle.get<Int>(CATEGORY_LIST_SCROLL_POSITION)

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

    val empty : LiveData<Boolean> = Transformations.map(_taskItems) {
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

    init {
        emitTasks()
        emitCategories()
    }

    fun emitTasks() = viewModelScope.launch {
        _taskFlow.onStart {
            // can use Loading State
            _taskDataLoading.value = true
        }.catch { _ ->
            // can use Error State
            showSnackBarMessage(R.string.loading_tasks_error)
        }.collect {
            _taskDataLoading.value = false
            if (it.isEmpty()) {
                _isExistTask.value = false
            } else {
                _isExistTask.value = true
                _taskItems.value = it
            }
        }
    }

    private fun emitCategories() = viewModelScope.launch {
        _categoryFlow.onStart {
            _categoryDataLoading.value = true
        }.catch {
            // can use Error State
            showSnackBarMessage(R.string.loading_categories_error)
        }.collect {
            _categoryDataLoading.value = false
            _categoryItems.value = it
        }
    }

    private fun showSnackBarMessage(message: Int) {
        _snackBarText.value = Event(message)
    }

    /** Cached Task RecyclerView Position */
    fun saveTaskScrollPosition(position: Int) {
        savedStateHandle.set(TASK_LIST_SCROLL_POSITION, position)
    }

    /** Cached Category RecyclerView Position */
    fun saveCategoryScrollPosition(position: Int) {
        savedStateHandle.set(CATEGORY_LIST_SCROLL_POSITION, position)
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

    companion object {
        private const val TASK_LIST_SCROLL_POSITION = "TASK_LIST_SCROLL_POSITION"
        private const val CATEGORY_LIST_SCROLL_POSITION = "CATEGORY_LIST_SCROLL_POSITION"
    }
}