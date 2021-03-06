package com.hks.kr.wifireminder.domain.repository

import com.hks.kr.wifireminder.data.source.local.entity.CategoryEntity
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.vo.Result
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun collectCategories(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: () -> Unit
    ): Flow<Result<List<Category>>>

    suspend fun insertCategory(categoryEntity: CategoryEntity)

    suspend fun getCategories(): Result<List<Category>>

    suspend fun deleteCategory(categoryName: String)

    suspend fun deleteCategories()
}