package com.hks.kr.wifireminder.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hks.kr.wifireminder.domain.entity.Task
import com.hks.kr.wifireminder.utils.DateUtil
import java.util.*

/**
 * @param title title of the task
 * @param description description of the task
 * @param category category of the task
 * @param startDate startDate of task
 * @param endDate endDate of task
 * @param importance importance of the task
 */

@Entity(tableName = "tasks")
data class TaskDTO(
    @PrimaryKey
    @ColumnInfo(name = "task_id")
    var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "start_data")
    val startDate: String = DateUtil.dateFormatBarWithOutTime.format(Date(System.currentTimeMillis())),
    @ColumnInfo(name = "end_date")
    val endDate: String,
    @ColumnInfo(name = "importance")
    val importance: Int
) {

    val titleForList: String
        get() = if (title.isNotEmpty()) title else description

    val isEmpty
        get() = title.isEmpty() || description.isEmpty()
}

fun List<TaskDTO>.asDomainTaskList(): List<Task> {
    return map {
        Task(
            id = it.id,
            title = it.title,
            description = it.description,
            category = it.category,
            startDate = it.startDate,
            endDate = it.endDate,
            importance = it.importance
        )
    }
}

fun TaskDTO.asDomainTask(): Task {
    return Task(
        id,
        title,
        description,
        category,
        startDate,
        endDate,
        importance
    )
}