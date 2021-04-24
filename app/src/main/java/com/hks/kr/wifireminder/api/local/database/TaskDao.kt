package com.hks.kr.wifireminder.api.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hks.kr.wifireminder.api.local.entity.TaskDTO

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskDTO: TaskDTO)

    @Query("SELECT * FROM user_task_table ORDER BY taskImportance DESC")
    suspend fun fetchTaskSortByImportance(): List<TaskDTO>

    @Query("DELETE FROM user_task_table WHERE :taskName = taskName")
    suspend fun deleteTask(taskName: String)

    @Query("DELETE FROM user_task_table")
    suspend fun deleteAllTask()
}