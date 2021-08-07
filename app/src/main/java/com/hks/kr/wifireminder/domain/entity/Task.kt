package com.hks.kr.wifireminder.domain.entity

/**
 * Domain Layer Task
 */
data class Task(
    val id : String,
    val title : String,
    val description : String,
    val category : String,
    val startDate : String,
    val endDate : String,
    val importance : Int
)