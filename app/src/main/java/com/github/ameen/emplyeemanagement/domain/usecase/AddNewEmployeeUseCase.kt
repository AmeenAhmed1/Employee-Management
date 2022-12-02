package com.github.ameen.emplyeemanagement.domain.usecase

import com.github.ameen.emplyeemanagement.domain.model.EmployeeDomainData
import com.github.ameen.emplyeemanagement.domain.repo.IEmployeeRepo
import javax.inject.Inject

class AddNewEmployeeUseCase @Inject constructor(private val repo: IEmployeeRepo) {
    suspend fun execute(employeeData: EmployeeDomainData): Long {
        return repo.addEmployee(employeeData)
    }
}