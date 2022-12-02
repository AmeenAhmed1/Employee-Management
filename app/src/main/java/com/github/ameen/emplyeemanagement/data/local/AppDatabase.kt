package com.github.ameen.emplyeemanagement.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.ameen.emplyeemanagement.data.local.converter.SkillsListTypeConverter
import com.github.ameen.emplyeemanagement.data.local.dao.EmployeeDao
import com.github.ameen.emplyeemanagement.data.local.dao.SkillsDao
import com.github.ameen.emplyeemanagement.data.local.entity.EmployeeEntity
import com.github.ameen.emplyeemanagement.data.local.entity.SkillsEntity

@Database(
    entities = [EmployeeEntity::class, SkillsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(SkillsListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun skillsDao(): SkillsDao
}