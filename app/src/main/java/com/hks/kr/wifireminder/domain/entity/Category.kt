package com.hks.kr.wifireminder.domain.entity

import android.graphics.drawable.Drawable
import java.sql.Blob

data class Category(
    val categoryName: String,
    val categoryCount: Int,
    val backgroundColorCode : String,
    val icon : Int
)