package com.hks.kr.wifireminder.domain.entity

data class Category(
    val id: String,
    val categoryName: String,
    val categoryCount: Int,
    val backgroundColorCode: String,
    val icon: Int
)