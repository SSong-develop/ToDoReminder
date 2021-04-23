package com.hks.kr.wifireminder.api.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hks.kr.wifireminder.domain.entity.TaskEntity

@Entity(tableName = "user_task_table")
data class TaskDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId")
    val id : Int = 0,
    @ColumnInfo(name = "taskName")
    val taskName : String,
    @ColumnInfo(name = "taskPeriod")
    val taskPeriod : Int,
    @ColumnInfo(name = "taskImportance")
    val taskImportance : Int
)

fun List<TaskDTO>.asDomainTaskEntity() : List<TaskEntity> {
    return map {
        TaskEntity(
            taskName = it.taskName,
            taskPeriod = it.taskPeriod,
            taskImportance = it.taskImportance
        )
    }
}