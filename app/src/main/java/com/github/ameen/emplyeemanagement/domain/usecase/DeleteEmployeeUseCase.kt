package com.github.ameen.emplyeemanagement.domain.usecase

import com.github.ameen.emplyeemanagement.domain.model.EmployeeDomainData
import com.github.ameen.emplyeemanagement.domain.repo.IEmployeeRepo
import javax.inject.Inject

class DeleteEmployeeUseCase @Inject constructor(private val repo: IEmployeeRepo) {
    suspend fun execute(employee: EmployeeDomainData): Boolean {
        repo.deleteEmployee(employee)
        return true
    }
}