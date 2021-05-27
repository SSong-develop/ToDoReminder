package com.hks.kr.wifireminder.api.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hks.kr.wifireminder.domain.entity.CategoryEntity

@Entity(tableName = "category_table")
data class CategoryDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    val categoryId: Int = 0,
    @ColumnInfo(name = "category_name")
    val categoryName: String,
    @ColumnInfo(name = "category_count")
    val categoryCount: Int
)

fun List<CategoryDTO>.asDomainCategoryEntity(): List<CategoryEntity> {
    return map {
        CategoryEntity(
            categoryName = it.categoryName,
            categoryCount = it.categoryCount,
        )
    }
}