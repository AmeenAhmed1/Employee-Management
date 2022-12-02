package com.github.ameen.emplyeemanagement

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.ameen.emplyeemanagement.data.local.AppDatabase
import com.github.ameen.emplyeemanagement.data.local.dao.SkillsDao
import com.github.ameen.emplyeemanagement.data.local.entity.SkillsEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SkillsDatabaseTest {

    private lateinit var skillsDao: SkillsDao
    private lateinit var db: AppDatabase

    val skills: ArrayList<SkillsEntity> = arrayListOf()

    private suspend fun initSkillsData(): List<Long> {

        repeat(3) {
            skills.add(
                SkillsEntity(it, "Skill$it")
            )
        }
        return skillsDao.insertSkills(skills)
    }

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()

        skillsDao = db.skillsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `Insert_Skill_List_Return_ListOfInsertedIds`() {
        runBlocking {
            val actualResult = initSkillsData()
            val expectedResult = listOf<Long>(0, 1, 2)
            Assert.assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `Get_All_Inserted_Skills`() {
        runBlocking {

            initSkillsData()

            val actualResult = skillsDao.getAllSkills()
            val expectedResult = skills

            Assert.assertEquals(expectedResult, actualResult)
        }
    }

}