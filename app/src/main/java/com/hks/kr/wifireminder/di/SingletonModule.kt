package com.hks.kr.wifireminder.di

import android.app.Application
import com.hks.kr.wifireminder.utils.PixelRatio
import com.hks.kr.wifireminder.utils.VersionCheckUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {
    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplication(app : Application) = app

    @Provides
    @Singleton
    fun providePixelRatio(@ApplicationContext context : Application) = PixelRatio(context)
}