package com.hks.kr.wifireminder.api.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hks.kr.wifireminder.api.local.entity.TaskDTO

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskDTO : TaskDTO)

    @Query("SELECT * FROM user_task_table ORDER BY taskImportance")
    fun fetchTaskSortByImportance() : LiveData<List<TaskDTO>>

    @Query("DELETE FROM user_task_table WHERE taskName = :taskName")
    fun deleteTask(taskName : String)
}