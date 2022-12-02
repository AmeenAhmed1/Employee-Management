package com.github.ameen.emplyeemanagement.domain.usecase

import com.github.ameen.emplyeemanagement.domain.model.SkillData
import com.github.ameen.emplyeemanagement.domain.repo.ISkillsRepo
import javax.inject.Inject

class GetSpecificSkillUseCase @Inject constructor(private val repo: ISkillsRepo) {
    suspend fun execute(skillId: Int): SkillData {
        return repo.getSpecificSkill(skillId)
    }
}