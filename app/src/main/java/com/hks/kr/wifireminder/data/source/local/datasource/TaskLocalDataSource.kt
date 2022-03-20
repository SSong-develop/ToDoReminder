package com.hks.kr.wifireminder.data.source.local.datasource

import com.hks.kr.wifireminder.data.source.local.dao.TaskDao
import com.hks.kr.wifireminder.data.source.local.entity.TaskEntity
import com.hks.kr.wifireminder.data.source.local.entity.asDomainTask
import com.hks.kr.wifireminder.data.source.local.entity.asDomainTaskList
import com.hks.kr.wifireminder.domain.entity.Task
import com.hks.kr.wifireminder.vo.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskLocalDataSource @Inject constructor(
    private val taskDao: TaskDao
) : TaskDataSource {

    override fun collectTasks(): Flow<Result<List<Task>>> {
        return taskDao.collectTasks().map {
            Result.Success(it.asDomainTaskList())
        }
    }

    override fun collectTask(taskId: String): Flow<Result<Task>> {
        return taskDao.collectTask(taskId).map {
            Result.Success(it.asDomainTask())
        }
    }

    override suspend fun getTasks(): Result<List<Task>> {
        return try {
            Result.Success(taskDao.getTasks().map {
                it.asDomainTask()
            })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTask(taskId: String): Result<Task> {
        return try {
            val task = taskDao.getTaskById(taskId)
            if (task != null) {
                Result.Success(task.asDomainTask())
            } else {
                Result.Error(Exception("Task not found!"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTaskByCategory(categoryTitle: String): Result<List<Task>> {
        return try {
            Result.Success(taskDao.fetchTaskByCategory(categoryTitle).asDomainTaskList())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTaskByImportance(): Result<List<Task>> {
        return try {
            Result.Success(taskDao.fetchTaskSortByImportance().asDomainTaskList())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    override suspend fun deleteAllTasks() {
        taskDao.deleteAllTask()
    }

    override suspend fun deleteTaskByEndDate(endDate: String) {
        taskDao.deleteTaskByDate(endDate)
    }

    override suspend fun getCategoryCount(categoryTitle: String): Int {
        return taskDao.getCategoryCount(categoryTitle)
    }

}