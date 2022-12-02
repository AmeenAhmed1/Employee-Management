package com.github.ameen.emplyeemanagement.presentation.fragment.create_edit_fragment

import androidx.lifecycle.viewModelScope
import com.github.ameen.emplyeemanagement.domain.model.EmployeeDomainData
import com.github.ameen.emplyeemanagement.domain.usecase.AddNewEmployeeUseCase
import com.github.ameen.emplyeemanagement.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEditViewModel @Inject constructor(
    private val addNewEmployeeUseCase: AddNewEmployeeUseCase
) : BaseViewModel() {

    private val _employeeDataInsertionState: MutableSharedFlow<Long> = MutableSharedFlow()
    val employeeDataInsertionState = _employeeDataInsertionState

    fun addNewEmployee(employeeData: EmployeeDomainData) = viewModelScope.launch(coroutineContext) {
        val result = addNewEmployeeUseCase.execute(employeeData)
        _employeeDataInsertionState.emit(result)
    }
}