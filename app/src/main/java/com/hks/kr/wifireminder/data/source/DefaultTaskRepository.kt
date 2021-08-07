package com.hks.kr.wifireminder.data.source

import androidx.lifecycle.LiveData
import com.hks.kr.wifireminder.data.Result
import com.hks.kr.wifireminder.data.TaskDTO
import com.hks.kr.wifireminder.data.source.local.TaskLocalDataSource
import com.hks.kr.wifireminder.domain.entity.Task
import com.hks.kr.wifireminder.utils.debugE
import kotlinx.coroutines.*
import javax.inject.Inject

class DefaultTaskRepository @Inject constructor(
    private val taskLocalDataSource: TaskLocalDataSource
) : TasksRepository {
    override fun observeTasks(): LiveData<Result<List<Task>>> {
        debugE(taskLocalDataSource.observeTasks().value)
        return taskLocalDataSource.observeTasks()
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        return taskLocalDataSource.observeTask(taskId)
    }

    override suspend fun getTasks(): Result<List<Task>> {
        return taskLocalDataSource.getTasks()
    }

    override suspend fun getTask(taskId: String): Result<Task> {
        return taskLocalDataSource.getTask(taskId)
    }

    override suspend fun getTaskByCategory(categoryTitle: String): Result<List<Task>> {
        return taskLocalDataSource.getTaskByCategory(categoryTitle)
    }

    override suspend fun getTaskByImportance(): Result<List<Task>> {
        return taskLocalDataSource.getTaskByImportance()
    }

    override suspend fun getCategoriesCount(): Int {
        return taskLocalDataSource.getCategoriesCount()
    }

    override suspend fun saveTask(task: TaskDTO) {
        coroutineScope {
            launch { taskLocalDataSource.saveTask(task) }
        }
    }

    override suspend fun deleteAllTasks() {
        coroutineScope {
            launch { taskLocalDataSource.deleteAllTasks() }
        }
    }

    override suspend fun deleteTaskByEndDate(endDate: String) {
        coroutineScope {
            launch { taskLocalDataSource.deleteTaskByEndDate(endDate) }
        }
    }

    override suspend fun getCategoryCount(categoryTitle: String): Int {
        return taskLocalDataSource.getCategoryCount(categoryTitle)
    }

}