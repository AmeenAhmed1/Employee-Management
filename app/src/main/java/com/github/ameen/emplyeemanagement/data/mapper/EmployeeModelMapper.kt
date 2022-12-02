package com.github.ameen.emplyeemanagement.data.mapper

import com.github.ameen.emplyeemanagement.data.local.entity.EmployeeEntity
import com.github.ameen.emplyeemanagement.domain.model.EmployeeDomainData

fun EmployeeEntity.toDomain(): EmployeeDomainData {
    return EmployeeDomainData(
        employeeId, employeeName, employeeEmail, employeeImage, employeeSkills
    )
}

fun EmployeeDomainData.toEntity(): EmployeeEntity {
    return EmployeeEntity(
        employeeId, employeeName, employeeEmail, employeeImage, employeeSkills
    )
}