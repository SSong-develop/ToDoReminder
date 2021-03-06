package com.hks.kr.wifireminder.data.source.local.entity

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hks.kr.wifireminder.domain.entity.Category
import java.util.*

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    var categoryId: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "category_title")
    val categoryTitle: String,
    @ColumnInfo(name = "background_color_code")
    val backgroundColorCode: String,
    @DrawableRes
    @ColumnInfo(name = "category_icon")
    val icon: Int
)

fun List<CategoryEntity>.asDomainCategoryEntity(): List<Category> {
    return map {
        Category(
            id = it.categoryId,
            categoryName = it.categoryTitle,
            backgroundColorCode = it.backgroundColorCode,
            icon = it.icon
        )
    }
}