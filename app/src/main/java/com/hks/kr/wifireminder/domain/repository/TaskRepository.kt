package com.hks.kr.wifireminder.domain.repository

import androidx.lifecycle.LiveData
import com.hks.kr.wifireminder.api.local.entity.TaskDTO

interface TaskRepository {
    suspend fun insertTask(taskDto : TaskDTO)

    suspend fun deleteTask(taskName : String)

    suspend fun fetchAllTaskSortByImportance() : List<TaskDTO>

    suspend fun deleteAllTask()
}