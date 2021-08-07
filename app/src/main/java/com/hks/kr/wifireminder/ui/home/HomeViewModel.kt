package com.hks.kr.wifireminder.ui.home

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    private val taskRepository: TasksRepository,
    private val categoryRepository: CategoryRepository,
    private val savedStateHandle : SavedStateHandle
) : ViewModel() {

    private val _taskEntityList = MutableLiveData<List<Task>?>(listOf())
    val taskEntityList: LiveData<List<Task>?>
        get() = _taskEntityList

    private val _taskItem: LiveData<List<Task>> =
        taskRepository.observeTasks().distinctUntilChanged().switchMap { filterTasks(it) }
    val taskItem: LiveData<List<Task>> = _taskItem

    private val _taskCategoryList = MutableLiveData<List<Category>>(listOf())
    val taskCategoryList: LiveData<List<Category>>
        get() = _taskCategoryList

    private val _allTaskCount = MutableLiveData<Int>()
    val allTaskCount: LiveData<Int>
        get() = _allTaskCount

    private val _snackBarText = MutableLiveData<Event<Int>>()
    val snackBarText: LiveData<Event<Int>>
        get() = _snackBarText

    init {
        mockTask()
        insertTestData()
        insertTestCategoryData()
        getAllTaskCount()
    }

    private fun insertTestData() = viewModelScope.launch {
        mockTask().also {
            taskRepository.getTasks().let { result ->
                if (result is Result.Success) {
                    _taskEntityList.value = result.data
                } else {
                    showSnackBarMessage(R.string.loading_tasks_error)
                }
            }
        }
    }

    private fun insertTestCategoryData() = viewModelScope.launch {
        runCatching {
            mockCategory()
        }.onSuccess {
            categoryRepository.getAllCategory().let {
                _taskCategoryList.postValue(it)
            }
        }.onFailure {
            // reTrial or just throw error
        }
    }

    fun fetchTaskByCategory(categoryName: String) = viewModelScope.launch {
        runCatching {
            taskRepository.getTaskByCategory(categoryName)
        }.onSuccess {
            if (it is Result.Success) {
                _taskEntityList.value = it.data
            }
        }.onFailure {
            // reTrial or just throw error
        }
    }

    fun fetchAllTask() = viewModelScope.launch {
        runCatching {
            taskRepository.getTaskByImportance()
        }.onSuccess {
            if (it is Result.Success) {
                _taskEntityList.value = it.data
            }
        }.onFailure {
            // reTrial or just throw error
        }
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

    private fun filterTasks(taskResult: Result<List<Task>>): LiveData<List<Task>> {
        val result = MutableLiveData<List<Task>>()

        if (taskResult is Result.Success) {
            viewModelScope.launch {
                result.value = filterItems(taskResult.data)
            }
        }
        return result
    }

    private fun filterItems(tasks: List<Task>): List<Task> {
        val tasksToShow = ArrayList<Task>()

        for (task in tasks) {
            tasksToShow.add(task)
        }
        return tasksToShow
    }

    private fun mockTask() = viewModelScope.launch {
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

    private fun mockCategory() = viewModelScope.launch {
        categoryRepository.insertCategory(
            CategoryDTO(
                1,
                "test1",
                taskRepository.getCategoryCount("test1")
            )
        )
        categoryRepository.insertCategory(
            CategoryDTO(
                2,
                "test2",
                taskRepository.getCategoryCount("test2")
            )
        )
        categoryRepository.insertCategory(
            CategoryDTO(
                3,
                "test3",
                taskRepository.getCategoryCount("test3")
            )
        )
        categoryRepository.insertCategory(
            CategoryDTO(
                4,
                "test4",
                taskRepository.getCategoryCount("test4")
            )
        )
    }
}