package com.hks.kr.wifireminder.data.repository

import com.hks.kr.wifireminder.data.source.local.datasource.TaskLocalDataSource
import com.hks.kr.wifireminder.data.source.local.entity.TaskEntity
import com.hks.kr.wifireminder.domain.entity.Task
import com.hks.kr.wifireminder.domain.repository.TasksRepository
import com.hks.kr.wifireminder.vo.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultTaskRepository @Inject constructor(
    private val taskLocalDataSource: TaskLocalDataSource
) : TasksRepository {

    override fun collectTasks(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: () -> Unit
    ): Flow<Result<List<Task>>> {
        return taskLocalDataSource.collectTasks().onStart {
            onStart()
        }.onCompletion {
            onComplete()
        }.catch {
            onError()
        }
    }

    override fun collectTask(
        taskId: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: () -> Unit
    ): Flow<Result<Task>> {
        return taskLocalDataSource.collectTask(taskId).onStart {
            onStart()
        }.onCompletion {
            onComplete()
        }.catch {
            onError()
        }
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

    override suspend fun saveTask(task: TaskEntity) = withContext(Dispatchers.IO) { taskLocalDataSource.saveTask(task) }

    override suspend fun deleteAllTasks() = withContext(Dispatchers.IO) { taskLocalDataSource.deleteAllTasks() }

    override suspend fun deleteTaskByEndDate(endDate: String) = withContext(Dispatchers.IO) { taskLocalDataSource.deleteTaskByEndDate(endDate) }

    override suspend fun getCategoryCount(categoryTitle: String): Int {
        return taskLocalDataSource.getCategoryCount(categoryTitle)
    }

}