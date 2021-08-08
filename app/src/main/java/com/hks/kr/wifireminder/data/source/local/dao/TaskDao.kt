package com.hks.kr.wifireminder.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.android.play.core.tasks.Task
import com.hks.kr.wifireminder.data.TaskDTO
import kotlinx.coroutines.flow.Flow

/**
 * Inner Join 사용해서 Category가 있는지 확인하고 그다음에 하는게 맞는거 같은데
 * JetCaster 보면서 좀 생각해보는게 맞을 듯 싶음
 *
 */
@Dao
interface TaskDao {

    /**
     * Observes list of tasks
     *
     *
     * @return all tasks
     */
    @Query("SELECT * FROM tasks ORDER BY importance DESC")
    fun observeTasks(): Flow<List<TaskDTO>>

    /**
     * Observes a single task
     *
     * @param taskId the task id
     * @return the task with taskId
     */
    @Query("SELECT * FROM tasks WHERE task_id = :taskId")
    fun observeTaskById(taskId: String): LiveData<TaskDTO>

    /**
     * Select all tasks from the tasks table
     *
     * @return all tasks
     */
    @Query("SELECT * FROM tasks")
    suspend fun getTasks(): List<TaskDTO>

    /**
     * Select a task by id
     *
     * @param taskId the task id
     * @return the task with taskId
     */
    @Query("SELECT * FROM tasks WHERE task_id = :taskId")
    suspend fun getTaskById(taskId: String): TaskDTO?

    /**
     * Insert a task in the database. If the task already exists, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskDTO: TaskDTO)

    @Transaction
    @Query("SELECT * FROM tasks ORDER BY importance DESC")
    suspend fun fetchTaskSortByImportance(): List<TaskDTO>

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
    suspend fun fetchTaskByCategory(categoryName: String): List<TaskDTO>

    /**
     * All Task Count
     */
    @Query("SELECT COUNT(*) FROM tasks")
    suspend fun getAllTaskCount(): Int
}