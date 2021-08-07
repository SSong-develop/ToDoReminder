package com.hks.kr.wifireminder.data.source

import com.hks.kr.wifireminder.data.CategoryDTO
import com.hks.kr.wifireminder.domain.entity.Category

interface CategoriesRepository {

    suspend fun insertCategory(categoryDTO: CategoryDTO)

    suspend fun getAllCategory(): List<Category>

    suspend fun deleteCategory(categoryName: String)
}