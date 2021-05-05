package com.hks.kr.wifireminder.datastore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
    suspend fun isAlreadySingleInvoked(): Boolean

    suspend fun isAlreadySingleInvokedWithFlow(): Flow<Boolean>

    suspend fun onSingleInvoke()
}