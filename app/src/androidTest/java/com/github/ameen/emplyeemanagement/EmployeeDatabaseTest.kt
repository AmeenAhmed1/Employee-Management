package com.github.ameen.emplyeemanagement

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.ameen.emplyeemanagement.data.local.AppDatabase
import com.github.ameen.emplyeemanagement.data.local.dao.EmployeeDao
import com.github.ameen.emplyeemanagement.data.local.dao.SkillsDao
import com.github.ameen.emplyeemanagement.data.local.entity.EmployeeEntity
import com.github.ameen.emplyeemanagement.data.local.entity.SkillsEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class EmployeeDatabaseTest {

    private lateinit var employeeDao: EmployeeDao
    private lateinit var skillsDao: SkillsDao
    private lateinit var db: AppDatabase

    private suspend fun initSkillsData() {
        val skills: ArrayList<SkillsEntity> = arrayListOf()
        repeat(3) {
            skills.add(
                SkillsEntity(it, "Skill$it")
            )
        }
        skillsDao.insertSkills(skills)
    }

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()

        employeeDao = db.employeeDao()
        skillsDao = db.skillsDao()

        runBlocking {
            initSkillsData()
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `Insert_NewEmployee_Without_EMAIL_IMAGE_SKILLS_Success`() {

        runBlocking {

            repeat(4) {
                employeeDao.insertEmployee(
                    EmployeeEntity(
                        employeeName = "Test Employee"
                    )
                )
            }

            val actualResult = employeeDao.getAllEmployees().isNotEmpty()

            assertEquals(true, actualResult)
        }
    }

    @Test
    fun `Insert_NewEmployee_With_SKILLS_Success`() {

        val employeeCount = 4
        val skillList = listOf(1, 3)

        runBlocking {

            repeat(employeeCount) {
                employeeDao.insertEmployee(
                    EmployeeEntity(
                        employeeName = "Test Employee",
                        employeeSkills = skillList
                    )
                )
            }

            val actualResult = employeeDao.getAllEmployees().filter {
                it.employeeSkills?.isNotEmpty() == true
            }

            assertEquals(employeeCount, actualResult.size)
        }
    }
}