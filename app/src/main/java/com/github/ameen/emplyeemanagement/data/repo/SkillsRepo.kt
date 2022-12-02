package com.github.ameen.emplyeemanagement.data.repo

import com.github.ameen.emplyeemanagement.data.local.AppDatabase
import com.github.ameen.emplyeemanagement.data.mapper.toDomain
import com.github.ameen.emplyeemanagement.data.mapper.toEntity
import com.github.ameen.emplyeemanagement.domain.model.SkillData
import com.github.ameen.emplyeemanagement.domain.model.SkillsDomainData
import com.github.ameen.emplyeemanagement.domain.repo.ISkillsRepo
import javax.inject.Inject

class SkillsRepo @Inject constructor(
    private val local: AppDatabase
) : ISkillsRepo {
    override suspend fun insertAllSkills(skillsData: List<SkillData>): List<Long> {
        return local.skillsDao().insertSkills(skillsData.map { it.toEntity() })
    }

    override suspend fun getAllSkills(): SkillsDomainData {
        val skills = local.skillsDao().getAllSkills().map { it.toDomain() }
        return SkillsDomainData(skills)
    }

    override suspend fun getSpecificSkill(skillId: Int): SkillData {
        return local.skillsDao().getSpecificSkill(skillId).toDomain()
    }

}