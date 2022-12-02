package com.github.ameen.emplyeemanagement.domain.repo

import com.github.ameen.emplyeemanagement.domain.model.EmployeeDomainData

interface IEmployeeRepo {
    suspend fun addEmployee(employeeData: EmployeeDomainData): Long
    suspend fun getAllEmployee(): List<EmployeeDomainData>
}