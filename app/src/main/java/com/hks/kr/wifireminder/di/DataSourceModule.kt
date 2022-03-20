package com.hks.kr.wifireminder.di

import com.hks.kr.wifireminder.data.source.local.datasource.TaskCategoryDataSource
import com.hks.kr.wifireminder.data.source.local.datasource.TaskCategoryLocalDataSource
import com.hks.kr.wifireminder.data.source.local.datasource.TaskDataSource
import com.hks.kr.wifireminder.data.source.local.datasource.TaskLocalDataSource
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

    @Binds
    @Singleton
    abstract fun bindTaskCategoryLocal(taskCategoryLocalDataSource: TaskCategoryLocalDataSource): TaskCategoryDataSource

}