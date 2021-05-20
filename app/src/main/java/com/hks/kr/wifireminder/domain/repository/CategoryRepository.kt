package com.hks.kr.wifireminder.domain.repository

import com.hks.kr.wifireminder.api.local.entity.CategoryDTO
import com.hks.kr.wifireminder.domain.entity.CategoryEntity

interface CategoryRepository {
    suspend fun insertCategory(categoryDTO: CategoryDTO)

    suspend fun getAllCategory(): List<CategoryEntity>

    suspend fun deleteCategory(categoryName: String)
}