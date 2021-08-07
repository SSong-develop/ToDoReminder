package com.hks.kr.wifireminder.di

import com.hks.kr.wifireminder.data.source.local.dao.CategoryDao
import com.hks.kr.wifireminder.data.source.local.dao.TaskDao
import com.hks.kr.wifireminder.data.repository.CategoryRepositoryImpl
import com.hks.kr.wifireminder.data.source.DefaultTaskRepository
import com.hks.kr.wifireminder.data.source.TasksRepository
import com.hks.kr.wifireminder.domain.repository.CategoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class TasksRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTasksRepository(tasksRepository : DefaultTaskRepository) : TasksRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(categoryRepository : CategoryRepositoryImpl) : CategoryRepository
}