package com.github.ameen.emplyeemanagement.presentation.fragment.create_edit_fragment

import androidx.lifecycle.viewModelScope
import com.github.ameen.emplyeemanagement.domain.model.EmployeeDomainData
import com.github.ameen.emplyeemanagement.domain.model.SkillData
import com.github.ameen.emplyeemanagement.domain.usecase.AddNewEmployeeUseCase
import com.github.ameen.emplyeemanagement.domain.usecase.GetAllSkillsUseCase
import com.github.ameen.emplyeemanagement.domain.usecase.GetSpecificSkillUseCase
import com.github.ameen.emplyeemanagement.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEditViewModel @Inject constructor(
    private val addNewEmployeeUseCase: AddNewEmployeeUseCase,
    private val getAllSkillsUseCase: GetAllSkillsUseCase,
    private val getSpecificSkillUseCase: GetSpecificSkillUseCase
) : BaseViewModel() {

    private val _employeeDataInsertionState: MutableSharedFlow<Long> = MutableSharedFlow()
    val employeeDataInsertionState = _employeeDataInsertionState

    private val _skillsDataState: MutableSharedFlow<List<SkillData>> = MutableSharedFlow()
    val skillsDataState = _skillsDataState

    fun addNewEmployee(employeeData: EmployeeDomainData) = viewModelScope.launch(coroutineContext) {
        val result = addNewEmployeeUseCase.execute(employeeData)
        _employeeDataInsertionState.emit(result)
    }

    fun getAllSkills() = viewModelScope.launch(coroutineContext) {
        val result = getAllSkillsUseCase.execute()
        _skillsDataState.emit(result.data)
    }

    fun getSpecificSkill(skillId: Int) =
        flow {
            val result = getSpecificSkillUseCase.execute(skillId)
            emit(result)
        }

}