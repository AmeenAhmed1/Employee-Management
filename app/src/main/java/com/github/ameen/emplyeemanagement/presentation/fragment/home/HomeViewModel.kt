package com.github.ameen.emplyeemanagement.presentation.fragment.home

import androidx.lifecycle.viewModelScope
import com.github.ameen.emplyeemanagement.domain.model.EmployeeDomainData
import com.github.ameen.emplyeemanagement.domain.usecase.GetAllEmployeesUseCase
import com.github.ameen.emplyeemanagement.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllEmployeesUseCase: GetAllEmployeesUseCase
) : BaseViewModel() {

    private val _employeeData: MutableSharedFlow<List<EmployeeDomainData>> = MutableSharedFlow()
    val employeeData = _employeeData

    fun getAllEmployees() = viewModelScope.launch(coroutineContext) {
        getAllEmployeesUseCase.execute().collectLatest {
            _employeeData.emit(it)
        }
    }


}