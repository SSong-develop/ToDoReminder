package com.hks.kr.wifireminder.data

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.domain.entity.Category
import java.sql.Blob

/**
 * TODO : Icon , Icon Background Color need To Insert
 */
@Entity(tableName = "category")
data class CategoryDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    var categoryId: Int = 0,
    @ColumnInfo(name = "category_title")
    val categoryTitle: String,
    @ColumnInfo(name = "task_count_by_category")
    val categoryCount: Int,
    @ColumnInfo(name = "background_color_code")
    val backgroundColorCode : String,
    @ColumnInfo(name = "category_icon")
    val icon : Int
)

fun List<CategoryDTO>.asDomainCategoryEntity(): List<Category> {
    return map {
        Category(
            categoryName = it.categoryTitle,
            categoryCount = it.categoryCount,
            backgroundColorCode = it.backgroundColorCode,
            icon = it.icon
        )
    }
}