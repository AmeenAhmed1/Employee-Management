package com.github.ameen.emplyeemanagement.presentation

import androidx.lifecycle.viewModelScope
import com.github.ameen.emplyeemanagement.domain.model.SkillData
import com.github.ameen.emplyeemanagement.domain.usecase.InsertAllSkillsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val insetAllSkillsUseCase: InsertAllSkillsUseCase
) : BaseViewModel() {

    private val _skillsDataInsertionState: MutableSharedFlow<List<Long>> = MutableSharedFlow()
    val skillsDataInsertionState = _skillsDataInsertionState

    fun insertAllSkills(skills: List<SkillData>) = viewModelScope.launch(coroutineContext) {
        val result = insetAllSkillsUseCase.execute(skills)
        _skillsDataInsertionState.emit(result)
    }
}
