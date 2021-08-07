package com.hks.kr.wifireminder.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hks.kr.wifireminder.data.CategoryDTO
import com.hks.kr.wifireminder.data.TaskDTO
import com.hks.kr.wifireminder.data.source.local.dao.CategoryDao
import com.hks.kr.wifireminder.data.source.local.dao.TaskDao

@Database(entities = [TaskDTO::class, CategoryDTO::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
    abstract val categoryDao: CategoryDao
}