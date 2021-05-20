package com.hks.kr.wifireminder.api.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hks.kr.wifireminder.api.local.entity.CategoryDTO

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryDTO: CategoryDTO)

    @Query("SELECT * FROM category_table")
    suspend fun getAllCategory(): List<CategoryDTO>

    @Query("DELETE FROM category_table WHERE :categoryName = category_name")
    suspend fun deleteCategory(categoryName: String)
}