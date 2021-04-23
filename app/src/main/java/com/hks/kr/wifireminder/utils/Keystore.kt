package com.hks.kr.wifireminder.utils

object Keystore {
    private val LocalDatabaseTableName = "user_task_table"

    fun provideLocalDatabaseTableName() = LocalDatabaseTableName
}