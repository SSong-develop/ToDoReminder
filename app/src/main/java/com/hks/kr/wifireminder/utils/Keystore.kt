package com.hks.kr.wifireminder.utils

object Keystore {
    private val LocalDatabaseTableName = "user_task_table"
    private const val TASK_NOTIFICATION_ID = 0
    private const val WIFI_NOTIFICATION_ID = 1
    private const val REQUEST_CODE = 0
    private const val FLAGS = 0

    fun provideLocalDatabaseTableName() = LocalDatabaseTableName

    fun provideTaskNotificationID() = TASK_NOTIFICATION_ID

    fun provideWifiNotificationID() = WIFI_NOTIFICATION_ID

    fun provideNotificationRequestCode() = REQUEST_CODE

    fun provideNotificationFLAGS() = FLAGS
}