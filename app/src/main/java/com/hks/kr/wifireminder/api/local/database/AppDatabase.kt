package com.hks.kr.wifireminder.api.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hks.kr.wifireminder.api.local.entity.TaskDTO

@Database(entities = [TaskDTO::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
}