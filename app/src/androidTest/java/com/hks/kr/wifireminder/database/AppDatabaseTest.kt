package com.hks.kr.wifireminder.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import com.hks.kr.wifireminder.api.local.database.AppDatabase
import com.hks.kr.wifireminder.api.local.entity.TaskDTO
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class AppDatabaseTest {
    private lateinit var db: AppDatabase

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.databaseBuilder(context, AppDatabase::class.java, "userTest")
            .allowMainThreadQueries().build()
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun insert() = runBlocking {
        val testList = listOf(
            TaskDTO(1, "1", "test1", 1, 1),
            TaskDTO(2, "2", "test2", 1, 2),
            TaskDTO(3, "3", "test3", 1, 3)
        )

        // 1
        // 1 2
        // 1 2 3
        testList.forEach {
            db.taskDao.insertTask(it)
        }

        // insert Test
        Truth.assertThat(db.taskDao.fetchTaskSortByImportance().size).isEqualTo(3)

        // 3 2 1
        Truth.assertThat(db.taskDao.fetchTaskSortByImportance()[0].taskName).isEqualTo("3")
        Truth.assertThat(db.taskDao.fetchTaskSortByImportance()[1].taskName).isEqualTo("2")
        Truth.assertThat(db.taskDao.fetchTaskSortByImportance()[2].taskName).isEqualTo("1")

        // 3 2
        db.taskDao.deleteTask("1")

        // size = 2
        Truth.assertThat(db.taskDao.fetchTaskSortByImportance().size).isEqualTo(2)
    }

}