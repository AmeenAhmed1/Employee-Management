package com.github.ameen.emplyeemanagement.domain.model

import java.io.Serializable

data class EmployeeDomainData(
    val employeeId: Int? = null,
    val employeeName: String,
    val employeeEmail: String? = null,
    val employeeImage: String? = null,
    val employeeSkills: List<Int> = listOf()
) : Serializable
