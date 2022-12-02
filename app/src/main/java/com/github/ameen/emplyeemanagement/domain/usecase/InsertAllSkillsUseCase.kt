package com.github.ameen.emplyeemanagement.domain.usecase

import com.github.ameen.emplyeemanagement.domain.model.SkillData
import com.github.ameen.emplyeemanagement.domain.repo.ISkillsRepo
import javax.inject.Inject

class InsertAllSkillsUseCase @Inject constructor(private val repo: ISkillsRepo) {
    suspend fun execute(skills: List<SkillData>): List<Long> {
        return repo.insertAllSkills(skills)
    }
}