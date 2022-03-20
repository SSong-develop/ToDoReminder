package com.hks.kr.wifireminder.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hks.kr.wifireminder.data.source.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun collectCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM category WHERE category_id = :categoryId")
    fun collectCategory(categoryId: Int): Flow<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM category")
    suspend fun getCategories(): List<CategoryEntity>

    @Query("DELETE FROM category WHERE :categoryName = category_title")
    suspend fun deleteCategory(categoryName: String)

    @Query("DELETE FROM category")
    suspend fun deleteCategories()
}