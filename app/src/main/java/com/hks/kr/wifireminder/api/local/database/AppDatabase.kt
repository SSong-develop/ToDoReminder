package com.hks.kr.wifireminder.api.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hks.kr.wifireminder.api.local.entity.CategoryDTO
import com.hks.kr.wifireminder.api.local.entity.TaskDTO

@Database(entities = [TaskDTO::class, CategoryDTO::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
    abstract val categoryDao: CategoryDao
}