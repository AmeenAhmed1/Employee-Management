package com.github.ameen.emplyeemanagement.data.local.dao

import androidx.room.*
import com.github.ameen.emplyeemanagement.data.local.entity.EmployeeEntity

@Dao
interface EmployeeDao {

    @Insert
    suspend fun insertEmployee(employeeData: EmployeeEntity): Long

    @Transaction
    @Query("SELECT * FROM EmployeeTable")
    suspend fun getAllEmployees(): List<EmployeeEntity>

    @Delete
    suspend fun deleteEmployee(employeeData: EmployeeEntity)
}