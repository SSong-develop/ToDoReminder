package com.hks.kr.wifireminder.domain.repository

import com.hks.kr.wifireminder.data.CategoryDTO
import com.hks.kr.wifireminder.domain.entity.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun observeCategories() : Flow<List<Category>>

    suspend fun insertCategory(categoryDTO: CategoryDTO)

    suspend fun getAllCategory(): List<Category>

    suspend fun deleteCategory(categoryName: String)
}