package com.hks.kr.wifireminder.di

import android.content.Context
import androidx.room.Room
import com.hks.kr.wifireminder.api.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "UserTaskDatabase")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideTaskDao(db: AppDatabase) = db.taskDao

    @Provides
    @Singleton
    fun provideCategoryDao(db: AppDatabase) = db.categoryDao
}