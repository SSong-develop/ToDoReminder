package com.hks.kr.wifireminder.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Domain Layer Task
 */
@Parcelize
data class Task(
    val id : String,
    val title : String,
    val description : String,
    val category : String,
    val startDate : String,
    val endDate : String,
    val importance : String
) : Parcelable