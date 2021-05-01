package com.hks.kr.wifireminder.authentication

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
    suspend fun isAlreadyWork() : Boolean

    suspend fun workingOnceCode()
}