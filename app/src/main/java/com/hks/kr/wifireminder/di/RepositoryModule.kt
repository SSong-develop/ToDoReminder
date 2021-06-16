package com.hks.kr.wifireminder.di

import com.hks.kr.wifireminder.api.local.database.CategoryDao
import com.hks.kr.wifireminder.api.local.database.TaskDao
import com.hks.kr.wifireminder.api.repository.CategoryRepositoryImpl
import com.hks.kr.wifireminder.api.repository.TaskRepositoryImpl
import com.hks.kr.wifireminder.domain.repository.CategoryRepository
import com.hks.kr.wifireminder.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// todo : 이부분 굉장히 이상함 다시 생각해보세요 송훈기 학생
@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository = TaskRepositoryImpl(taskDao)

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository =
        CategoryRepositoryImpl(categoryDao)
}