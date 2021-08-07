package com.hks.kr.wifireminder.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hks.kr.wifireminder.domain.entity.Category

@Entity(tableName = "category")
data class CategoryDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryId")
    var categoryId: Int = 0,
    @ColumnInfo(name = "category_title")
    val categoryTitle: String,
    @ColumnInfo(name = "category_count")
    val categoryCount: Int
)

fun List<CategoryDTO>.asDomainCategoryEntity(): List<Category> {
    return map {
        Category(
            categoryName = it.categoryTitle,
            categoryCount = it.categoryCount,
        )
    }
}