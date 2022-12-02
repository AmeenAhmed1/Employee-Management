package com.github.ameen.emplyeemanagement.domain.usecase

import com.github.ameen.emplyeemanagement.domain.model.EmployeeDomainData
import com.github.ameen.emplyeemanagement.domain.repo.IEmployeeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllEmployeesUseCase @Inject constructor(private val repo: IEmployeeRepo) {
    suspend fun execute(employeeName: String): Flow<List<EmployeeDomainData>> {
        return flow {
            emit(repo.getAllEmployee(employeeName))
        }
    }
}