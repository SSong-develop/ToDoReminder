package com.hks.kr.wifireminder.datastore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
    suspend fun isAlreadyWorked() : Boolean

    suspend fun isAlreadyWorkedWithFlow() : Flow<Boolean>

    suspend fun runOnlyOnce()
}