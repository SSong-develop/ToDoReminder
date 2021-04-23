package com.hks.kr.wifireminder.api.repository

import androidx.lifecycle.LiveData
import com.hks.kr.wifireminder.api.local.database.TaskDao
import com.hks.kr.wifireminder.api.local.entity.TaskDTO
import com.hks.kr.wifireminder.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val taskDao : TaskDao
) : TaskRepository {
    override suspend fun insertTask(taskDto: TaskDTO){
        taskDao.insertTask(taskDto)
    }

    override suspend fun deleteTask(taskName: String) {
        taskDao.deleteTask(taskName)
    }

    override suspend fun fetchAllTaskSortByImportance(): List<TaskDTO> = taskDao.fetchTaskSortByImportance()
}