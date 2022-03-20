package com.hks.kr.wifireminder.data.repository

import com.hks.kr.wifireminder.data.source.local.datasource.TaskCategoryDataSource
import com.hks.kr.wifireminder.data.source.local.entity.CategoryEntity
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.domain.repository.CategoryRepository
import com.hks.kr.wifireminder.vo.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val taskCategoryDataSource: TaskCategoryDataSource
) : CategoryRepository {

    override fun collectCategories(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: () -> Unit
    ): Flow<Result<List<Category>>> {
        return taskCategoryDataSource.collectCategories().onStart {
            onStart()
        }.onCompletion {
            onComplete()
        }.catch {
            onError()
        }
    }

    override suspend fun insertCategory(categoryEntity: CategoryEntity) {
        taskCategoryDataSource.insertCategory(categoryEntity)
    }

    override suspend fun getCategories(): Result<List<Category>> {
        return taskCategoryDataSource.getCategories()
    }

    override suspend fun deleteCategory(categoryName: String) {
        taskCategoryDataSource.deleteCategory(categoryName)
    }

    override suspend fun deleteCategories() {
        taskCategoryDataSource.deleteCategories()
    }
}