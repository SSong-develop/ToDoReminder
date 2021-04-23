package com.hks.kr.wifireminder.di

import com.hks.kr.wifireminder.api.repository.TaskRepositoryImpl
import com.hks.kr.wifireminder.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTaskRepository(repository : TaskRepositoryImpl) : TaskRepository
}