package com.hks.kr.wifireminder.data

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.domain.entity.Category
import java.sql.Blob
import java.util.*

@Entity(tableName = "category")
data class CategoryDTO(
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    var categoryId: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "category_title")
    val categoryTitle: String,
    @ColumnInfo(name = "task_count_by_category")
    val categoryCount: Int,
    @ColumnInfo(name = "background_color_code")
    val backgroundColorCode : String,
    @DrawableRes
    @ColumnInfo(name = "category_icon")
    val icon : Int
)

fun List<CategoryDTO>.asDomainCategoryEntity(): List<Category> {
    return map {
        Category(
            id = it.categoryId,
            categoryName = it.categoryTitle,
            categoryCount = it.categoryCount,
            backgroundColorCode = it.backgroundColorCode,
            icon = it.icon
        )
    }
}