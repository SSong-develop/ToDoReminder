package com.hks.kr.wifireminder.api.local.entity

data class TaskDTO(
    val taskName : String,
    val taskPeriod : Int,
    val taskImportance : Int
)

