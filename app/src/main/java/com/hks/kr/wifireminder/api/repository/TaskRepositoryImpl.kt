package com.hks.kr.wifireminder.api.repository

import com.hks.kr.wifireminder.api.local.database.TaskDao
import com.hks.kr.wifireminder.api.local.entity.TaskDTO
import com.hks.kr.wifireminder.api.local.entity.asDomainTaskEntity
import com.hks.kr.wifireminder.domain.entity.TaskEntity
import com.hks.kr.wifireminder.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {
    override suspend fun insertTask(taskDto: TaskDTO) {
        taskDao.insertTask(taskDto)
    }

    override suspend fun deleteTask(taskName: String) {
        taskDao.deleteTask(taskName)
    }

    override suspend fun fetchAllTaskSortByImportance(): List<TaskEntity> =
        taskDao.fetchTaskSortByImportance().asDomainTaskEntity()

    override suspend fun deleteAllTask() {
        taskDao.deleteAllTask()
    }

    override suspend fun getCategoryCount(categoryName: String): Int =
        taskDao.getCategoryCount(categoryName)

    override suspend fun fetchTaskByCategory(categoryName: String): List<TaskEntity> =
        taskDao.fetchTaskByCategory(categoryName).asDomainTaskEntity()

    override suspend fun getAllTaskCount(): Int = taskDao.getAllTaskCount()
}