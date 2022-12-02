package com.github.ameen.emplyeemanagement.presentation.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ameen.emplyeemanagement.domain.model.EmployeeDomainData
import com.github.ameen.emplyeemanagement.domain.usecase.GetAllEmployeesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllEmployeesUseCase: GetAllEmployeesUseCase
) : ViewModel() {

    private val coroutineJob: Job = Job()
    private val coroutineDispatcher = Dispatchers.IO
    private val coroutineContext: CoroutineContext = coroutineJob + coroutineDispatcher

    private val _employeeData: MutableSharedFlow<List<EmployeeDomainData>> = MutableSharedFlow()
    val employeeData = _employeeData

    fun getAllEmployees() = viewModelScope.launch(coroutineContext) {
        getAllEmployeesUseCase.execute().collectLatest {
            _employeeData.emit(it)
        }
    }

}