package com.hks.kr.wifireminder.di

import com.hks.kr.wifireminder.authentication.PrefsStore
import com.hks.kr.wifireminder.authentication.PrefsStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Binds
    @Singleton
    abstract fun bindDataStorePreference(dataStore : PrefsStoreImpl) : PrefsStore
}