package com.hks.kr.wifireminder.api.repository

import com.hks.kr.wifireminder.api.local.database.CategoryDao
import com.hks.kr.wifireminder.api.local.entity.CategoryDTO
import com.hks.kr.wifireminder.api.local.entity.asDomainCategoryEntity
import com.hks.kr.wifireminder.domain.entity.CategoryEntity
import com.hks.kr.wifireminder.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override suspend fun insertCategory(categoryDTO: CategoryDTO) {
        categoryDao.insertCategory(categoryDTO)
    }

    override suspend fun getAllCategory(): List<CategoryEntity> =
        categoryDao.getAllCategory().asDomainCategoryEntity()

    override suspend fun deleteCategory(categoryName: String) {
        categoryDao.deleteCategory(categoryName)
    }
}