package com.hks.kr.wifireminder.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.hks.kr.wifireminder.api.local.database.AppDatabase
import com.hks.kr.wifireminder.api.local.entity.TaskDTO
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class AppDatabaseTest {
    private lateinit var db : AppDatabase

    @Before
    fun createDB(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.databaseBuilder(context,AppDatabase::class.java,"userTest").allowMainThreadQueries().build()
    }

    @After
    fun closeDB(){
        db.close()
    }

    @Test
    fun insert() = runBlocking {
        val testList = listOf(
            TaskDTO("1",1,1),
            TaskDTO("2",1,2),
            TaskDTO("3",1,3)
        )

        testList.forEach {
            db.taskDao.insertTask(it)
        }

        assert(db.taskDao.fetchTaskSortByImportance().size == 3)

        assert(db.taskDao.fetchTaskSortByImportance()[0].taskName == "1")
        assert(db.taskDao.fetchTaskSortByImportance()[1].taskName == "2")
        assert(db.taskDao.fetchTaskSortByImportance()[2].taskName == "3")
    }

}