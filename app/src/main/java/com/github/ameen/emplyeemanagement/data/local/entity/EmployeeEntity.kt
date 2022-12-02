package com.github.ameen.emplyeemanagement.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EmployeeTable")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    val employeeId: Int? = null,
    val employeeName: String,
    val employeeEmail: String? = null,
    val employeeImage: String? = null,
    val employeeSkills: List<Int> = listOf()
)
