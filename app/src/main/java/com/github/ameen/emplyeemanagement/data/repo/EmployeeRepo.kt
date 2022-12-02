package com.github.ameen.emplyeemanagement.data.repo

import com.github.ameen.emplyeemanagement.data.local.AppDatabase
import com.github.ameen.emplyeemanagement.data.mapper.toDomain
import com.github.ameen.emplyeemanagement.data.mapper.toEntity
import com.github.ameen.emplyeemanagement.domain.model.EmployeeDomainData
import com.github.ameen.emplyeemanagement.domain.repo.IEmployeeRepo
import javax.inject.Inject

class EmployeeRepo @Inject constructor(
    private val local: AppDatabase
) : IEmployeeRepo {

    override suspend fun insertEmployee(employeeData: EmployeeDomainData) {
        local.employeeDao().insertEmployee(employeeData.toEntity())
    }

    override suspend fun getAllEmployee(): List<EmployeeDomainData> {
        return local.employeeDao().getAllEmployees().map { it.toDomain() }
    }
}