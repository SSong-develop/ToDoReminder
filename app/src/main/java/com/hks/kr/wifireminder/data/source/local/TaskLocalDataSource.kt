package com.hks.kr.wifireminder.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.hks.kr.wifireminder.data.*
import com.hks.kr.wifireminder.data.source.TaskDataSource
import com.hks.kr.wifireminder.data.source.local.dao.TaskDao
import com.hks.kr.wifireminder.domain.entity.Task
import com.hks.kr.wifireminder.utils.debugE
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskLocalDataSource @Inject constructor(
    private val taskDao: TaskDao
) : TaskDataSource {
    override fun observeTasks(): LiveData<Result<List<Task>>> {
        return taskDao.observeTasks().map {
            Result.Success(it.asDomainTaskList())
        }
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        return taskDao.observeTaskById(taskId).map {
            Result.Success(it.asDomainTask())
        }
    }

    override suspend fun getTasks(): Result<List<Task>> {
        return try {
            Result.Success(taskDao.getTasks().asDomainTaskList())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTask(taskId: String): Result<Task> {
        try {
            val task = taskDao.getTaskById(taskId)
            if (task != null) {
                return Result.Success(task.asDomainTask())
            } else {
                return Result.Error(Exception("Task not found!"))
            }
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun getTaskByCategory(categoryTitle: String): Result<List<Task>> {
        return try {
            Result.Success(taskDao.fetchTaskByCategory(categoryTitle).asDomainTaskList())
        } catch (e : Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTaskByImportance(): Result<List<Task>> {
        return try {
            Result.Success(taskDao.fetchTaskSortByImportance().asDomainTaskList())
        } catch (e : Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getCategoriesCount(): Int {
        return try {
            taskDao.getAllTaskCount()
        } catch (e : Exception){
            debugE(e)
            -1
        }
    }

    override suspend fun saveTask(task: TaskDTO) {
        taskDao.insertTask(task)
    }

    override suspend fun deleteAllTasks() {
        taskDao.deleteAllTask()
    }

    override suspend fun deleteTaskByEndDate(endDate: String) {
        taskDao.deleteTaskByDate(endDate)
    }

    override suspend fun getCategoryCount(categoryTitle : String): Int {
        return taskDao.getCategoryCount(categoryTitle)
    }

}