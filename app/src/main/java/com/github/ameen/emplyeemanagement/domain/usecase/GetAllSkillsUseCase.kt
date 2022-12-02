package com.github.ameen.emplyeemanagement.domain.usecase

import com.github.ameen.emplyeemanagement.domain.model.SkillsDomainData
import com.github.ameen.emplyeemanagement.domain.repo.ISkillsRepo
import javax.inject.Inject

class GetAllSkillsUseCase @Inject constructor(private val repo: ISkillsRepo) {
    suspend fun execute(): SkillsDomainData {
        return repo.getAllSkills()
    }
}