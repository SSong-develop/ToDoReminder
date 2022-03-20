package com.hks.kr.wifireminder.data.source.local.dao

import androidx.room.*
import com.hks.kr.wifireminder.data.source.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    /**
     * Observes list of tasks
     *
     *
     * @return all tasks
     */
    @Query("SELECT * FROM tasks ORDER BY importance DESC")
    fun collectTasks(): Flow<List<TaskEntity>>

    /**
     * Observes a single task
     *
     * @param taskId the task id
     * @return the task with taskId
     */
    @Query("SELECT * FROM tasks WHERE task_id = :taskId")
    fun collectTask(taskId: String): Flow<TaskEntity>

    /**
     * Select all tasks from the tasks table
     *
     * @return all tasks
     */
    @Query("SELECT * FROM tasks")
    suspend fun getTasks(): List<TaskEntity>

    /**
     * Select a task by id
     *
     * @param taskId the task id
     * @return the task with taskId
     */
    @Query("SELECT * FROM tasks WHERE task_id = :taskId")
    suspend fun getTaskById(taskId: String): TaskEntity?

    /**
     * Insert a task in the database. If the task already exists, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskDTO: TaskEntity)

    @Transaction
    @Query("SELECT * FROM tasks ORDER BY importance DESC")
    suspend fun fetchTaskSortByImportance(): List<TaskEntity>

    /**
     * Delete a task by id
     *
     * @return the number of tasks deleted. it will be always 1
     */
    @Query("DELETE FROM tasks WHERE task_id = :taskId")
    suspend fun deleteTaskById(taskId: String): Int

    /**
     * Delete a task by endDate
     *
     * @return the number of tasks deleted , it will not specify number
     */
    @Query("DELETE FROM tasks WHERE end_date = :endDate")
    suspend fun deleteTaskByDate(endDate: String): Int

    /**
     * Delete All Tasks
     */
    @Query("DELETE FROM tasks")
    suspend fun deleteAllTask()

    /**
     * Category Count
     */
    @Query("SELECT COUNT(*) FROM tasks WHERE :categoryName = category")
    suspend fun getCategoryCount(categoryName: String): Int

    /**
     * get Specify Category Task
     */
    @Query("SELECT * FROM tasks WHERE :categoryName = category")
    suspend fun fetchTaskByCategory(categoryName: String): List<TaskEntity>
}