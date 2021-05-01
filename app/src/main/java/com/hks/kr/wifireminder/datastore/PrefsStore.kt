package com.hks.kr.wifireminder.datastore

interface PrefsStore {
    suspend fun isAlreadyWorked() : Boolean

    suspend fun runOnlyOnce()
}