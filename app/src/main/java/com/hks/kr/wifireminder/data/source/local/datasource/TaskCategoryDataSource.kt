package com.hks.kr.wifireminder.data.source.local.datasource

import com.hks.kr.wifireminder.data.source.local.entity.CategoryEntity
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.vo.Result
import kotlinx.coroutines.flow.Flow

interface TaskCategoryDataSource {

    /**
     * Return all categories wraps in a flow
     */
    fun collectCategories(): Flow<Result<List<Category>>>

    /**
     * Insert specific category
     */
    suspend fun insertCategory(categoryEntity: CategoryEntity)

    /**
     * Return all categories
     */
    suspend fun getCategories(): Result<List<Category>>

    /**
     * Delete specific category
     */
    suspend fun deleteCategory(categoryName: String)

    /**
     * Delete all category
     */
    suspend fun deleteCategories()
}