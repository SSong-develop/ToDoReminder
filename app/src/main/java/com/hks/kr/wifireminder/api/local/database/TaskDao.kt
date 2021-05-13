package com.hks.kr.wifireminder.api.local.database

import androidx.room.*
import com.hks.kr.wifireminder.api.local.entity.TaskDTO

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskDTO: TaskDTO)

    @Transaction
    @Query("SELECT * FROM task_table ORDER BY taskImportance DESC")
    suspend fun fetchTaskSortByImportance(): List<TaskDTO>

    @Query("DELETE FROM task_table WHERE :taskName = taskName")
    suspend fun deleteTask(taskName: String)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTask()

    @Query("SELECT COUNT(*) FROM task_table WHERE :categoryName = taskCategory")
    suspend fun getCategoryCount(categoryName : String) : Int
}