package com.hks.kr.wifireminder.di

import com.hks.kr.wifireminder.api.local.database.TaskDao
import com.hks.kr.wifireminder.api.repository.TaskRepositoryImpl
import com.hks.kr.wifireminder.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository = TaskRepositoryImpl(taskDao)
}