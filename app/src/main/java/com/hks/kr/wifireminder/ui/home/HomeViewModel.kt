package com.hks.kr.wifireminder.ui.home

import android.app.Application
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.*
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.data.CategoryDTO
import com.hks.kr.wifireminder.data.Result
import com.hks.kr.wifireminder.data.TaskDTO
import com.hks.kr.wifireminder.data.source.TasksRepository
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.domain.entity.Task
import com.hks.kr.wifireminder.domain.repository.CategoryRepository
import com.hks.kr.wifireminder.utils.Event
import com.hks.kr.wifireminder.utils.debugE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.inject.Inject

/**
 * TODO : 처음에 앱이 켜질 때 왜 task 개수들이 1개씩 밖에 안보이는 이슈가 있는지??? 이거 알아봐야 할듯 싶습니다.
 *
 * TODO : Color를 계절별로 나타내고 싶은데 방법이 마땅하게 떠오르질 않아
 *
 * TODO : 할일을 끝냈는지 아닌지에 대한 결과를 가져오는??? 그런것도 있으면 좋을거 같아요 , 특정 버튼을 누르면 이제 할일을 끝냈다는 표시인거고
 * TODO : 정해진 시간이 끝나면 자동으로 이를 WorkManager가 지워버리는 거죠
 *
 * TODO : LiveData를 과연 내가 제대로 형식을 짜서 쓰고 있는건지를 생각해볼 필요가 있어 훈기야
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val applicationContext : Application,
    private val taskRepository: TasksRepository,
    private val categoryRepository: CategoryRepository,
    private val savedStateHandle : SavedStateHandle
) : ViewModel() {

    private val _taskFlow: Flow<List<Task>> =
        taskRepository.observeTasks()

    private val _categoryFlow : Flow<List<Category>> =
        categoryRepository.observeCategories()

    private val _taskItems = MutableLiveData<List<Task>>(listOf())
    val taskItems : LiveData<List<Task>>
        get() = _taskItems

    private val _categoryItems = MutableLiveData<List<Category>>(listOf())
    val categoryItems: LiveData<List<Category>>
        get() = _categoryItems

    // TODO : 이부분 별로임
    private val _allTaskCount = MutableLiveData<Int>()
    val allTaskCount: LiveData<Int>
        get() = _allTaskCount

    /**
     * SnackBar Event LiveData
     */
    private val _snackBarText = MutableLiveData<Event<Int>>()
    val snackBarText: LiveData<Event<Int>>
        get() = _snackBarText

    /**
     * DataLoading UI State LiveData
     */
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading : LiveData<Boolean> = _dataLoading

    init {
        mockTask()
        mockCategory()
        getAllTaskCount()

        emitTasks()
        emitCategories()
    }

    private fun emitTasks() = viewModelScope.launch {
        _taskFlow.onStart {
            // can use Loading State
            _dataLoading.value = true
        }.onCompletion {
            _dataLoading.value = false
        }.catch { _ ->
            // can use Error State
            showSnackBarMessage(R.string.loading_tasks_error)
        }.collect {
            _taskItems.value = it
        }
    }

    private fun emitCategories() = viewModelScope.launch {
        _categoryFlow.onStart {
            // can use Loading State
            _dataLoading.value = true
        }.onCompletion {
            _dataLoading.value = false
        }.catch {
            // can use Error State
            showSnackBarMessage(R.string.loading_categories_error)
        }.collect {
            _categoryItems.value = it
        }
    }

    private fun emitTasksCount(){
        _allTaskCount.value = _taskItems.value?.size
    }

    private fun getAllTaskCount() = viewModelScope.launch {
        runCatching {
            taskRepository.getCategoriesCount()
        }.onSuccess {
            _allTaskCount.value = it
        }.onFailure {
            // reTrial or just throw error
        }
    }

    private fun showSnackBarMessage(message: Int) {
        _snackBarText.value = Event(message)
    }

    private fun mockTask() = viewModelScope.launch(Dispatchers.IO) {
        taskRepository.saveTask(
            TaskDTO(
                id = "1",
                title = "[Test] 송훈기",
                description = "살려줘 송훈기",
                category = "test1",
                endDate = "2021-09-01",
                importance = 1
            )
        )
        taskRepository.saveTask(
            TaskDTO(
                id = "2",
                title = "[Test] 송훈기1",
                description = "살려줘 송훈기1",
                category = "test2",
                endDate = "2021-09-02",
                importance = 2
            )
        )
        taskRepository.saveTask(
            TaskDTO(
                id = "3",
                title = "[Test] 송훈기2",
                description = "살려줘 송훈기2",
                category = "test3",
                endDate = "2021-09-03",
                importance = 3
            )
        )
        taskRepository.saveTask(
            TaskDTO(
                id = "4",
                title = "[Test] 송훈기3",
                description = "살려줘 송훈기3",
                category = "test4",
                endDate = "2021-09-04",
                importance = 4
            )
        )
    }

    private fun mockCategory() = viewModelScope.launch(Dispatchers.IO) {
        categoryRepository.insertCategory(
            CategoryDTO(
                1,
                "test1",
                taskRepository.getCategoryCount("test1"),
                "#AA11AA",
                R.drawable.ic_home
            )
        )
        categoryRepository.insertCategory(
            CategoryDTO(
                2,
                "test2",
                taskRepository.getCategoryCount("test2"),
                "#4D6B8FF9",
                R.drawable.ic_person
            )
        )
        categoryRepository.insertCategory(
            CategoryDTO(
                3,
                "test3",
                taskRepository.getCategoryCount("test3"),
                "#AA11AA",
                R.drawable.ic_add
            )
        )
        categoryRepository.insertCategory(
            CategoryDTO(
                4,
                "test4",
                taskRepository.getCategoryCount("test4"),
                "#4D6B8FF9",
                R.drawable.ic_home
            )
        )
    }
}