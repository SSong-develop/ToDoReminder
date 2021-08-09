package com.hks.kr.wifireminder.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hks.kr.wifireminder.data.CategoryDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun observeCategories() : Flow<List<CategoryDTO>>

    @Query("SELECT * FROM category WHERE category_id = :categoryId")
    fun observeCategoryById(categoryId : Int) : LiveData<CategoryDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryDTO: CategoryDTO)

    @Query("SELECT * FROM category")
    suspend fun getAllCategory(): List<CategoryDTO>

    @Query("DELETE FROM category WHERE :categoryName = category_title")
    suspend fun deleteCategory(categoryName: String)
}