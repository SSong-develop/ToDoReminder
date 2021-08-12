package com.hks.kr.wifireminder.di

import com.hks.kr.wifireminder.data.source.TaskDataSource
import com.hks.kr.wifireminder.data.source.local.TaskLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindTaskLocalDataSource(taskLocalDataSource: TaskLocalDataSource): TaskDataSource

}