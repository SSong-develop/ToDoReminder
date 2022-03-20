package com.hks.kr.wifireminder.data.source.local.datasource

import com.hks.kr.wifireminder.data.source.local.dao.CategoryDao
import com.hks.kr.wifireminder.data.source.local.entity.CategoryEntity
import com.hks.kr.wifireminder.data.source.local.entity.asDomainCategoryEntity
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.vo.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskCategoryLocalDataSource @Inject constructor(
    private val categoryDao: CategoryDao
) : TaskCategoryDataSource {
    override fun collectCategories(): Flow<Result<List<Category>>> {
        return categoryDao.collectCategories().map {
            Result.Success(it.asDomainCategoryEntity())
        }
    }

    override suspend fun insertCategory(categoryEntity: CategoryEntity) {
        categoryDao.insertCategory(categoryEntity)
    }

    override suspend fun getCategories(): Result<List<Category>> {
        return Result.Success(categoryDao.getCategories().asDomainCategoryEntity())
    }

    override suspend fun deleteCategory(categoryName: String) {
        categoryDao.deleteCategory(categoryName)
    }

    override suspend fun deleteCategories() {
        categoryDao.deleteCategories()
    }
}