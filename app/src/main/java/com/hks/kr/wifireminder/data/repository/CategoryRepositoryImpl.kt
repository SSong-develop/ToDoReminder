package com.hks.kr.wifireminder.data.repository

import com.hks.kr.wifireminder.data.source.local.dao.CategoryDao
import com.hks.kr.wifireminder.data.CategoryDTO
import com.hks.kr.wifireminder.data.asDomainCategoryEntity
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun observeCategories(): Flow<List<Category>> {
        return categoryDao.observeCategories().map { it.asDomainCategoryEntity() }
    }

    override suspend fun insertCategory(categoryDTO: CategoryDTO) {
        categoryDao.insertCategory(categoryDTO)
    }

    override suspend fun getAllCategory(): List<Category> =
        categoryDao.getAllCategory().asDomainCategoryEntity()

    override suspend fun deleteCategory(categoryName: String) {
        categoryDao.deleteCategory(categoryName)
    }

    override suspend fun deleteCategories() {
        categoryDao.deleteCategories()
    }
}