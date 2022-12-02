package com.github.ameen.emplyeemanagement.data.local.dao

import androidx.room.*
import com.github.ameen.emplyeemanagement.data.local.entity.EmployeeEntity

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employeeData: EmployeeEntity): Long

    @Transaction
    @Query("SELECT * FROM EmployeeTable WHERE employeeName LIKE ('%' || :employeeName || '%')")
    suspend fun getAllEmployees(employeeName: String): List<EmployeeEntity>

    @Delete
    suspend fun deleteEmployee(employeeData: EmployeeEntity)
}