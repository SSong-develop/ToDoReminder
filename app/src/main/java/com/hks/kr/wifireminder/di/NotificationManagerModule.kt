package com.hks.kr.wifireminder.di

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationManagerModule {

    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context) =
        ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
}