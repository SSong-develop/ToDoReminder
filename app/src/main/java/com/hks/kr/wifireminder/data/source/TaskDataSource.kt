package com.hks.kr.wifireminder.data.source

import androidx.lifecycle.LiveData
import com.hks.kr.wifireminder.vo.Result
import com.hks.kr.wifireminder.data.TaskDTO
import com.hks.kr.wifireminder.domain.entity.Task
import kotlinx.coroutines.flow.Flow

/**
 * TODO : 전부 Result로 감싸줄거임 안되면 될때 까지 할거야
 *
 * TODO : Observe란 단어가 과연 어울리는지를 모르겠다;;
 *
 * TODO : Naming 룰에 대해서 좀 확실하게 명시를 하고 그 다음에 생각을 하자
 */
interface TaskDataSource {

    fun observeTask(taskId: String): LiveData<Result<Task>>

    fun observeTaskByLiveData() : LiveData<Result<List<Task>>>

    fun observeTasks(): Flow<List<Task>>

    suspend fun getTasks(): Result<List<Task>>

    suspend fun getTask(taskId: String): Result<Task>

    suspend fun getTaskByCategory(categoryTitle: String): Result<List<Task>>

    suspend fun getTaskByImportance(): Result<List<Task>>

    suspend fun saveTask(task: TaskDTO)

    suspend fun deleteAllTasks()

    suspend fun deleteTaskByEndDate(endDate: String)

    suspend fun getCategoryCount(categoryTitle: String): Int
}