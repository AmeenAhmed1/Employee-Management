package com.github.ameen.emplyeemanagement.domain.repo

import com.github.ameen.emplyeemanagement.domain.model.SkillData
import com.github.ameen.emplyeemanagement.domain.model.SkillsDomainData

interface ISkillsRepo {
    suspend fun insertAllSkills(skillsData: List<SkillData>): List<Long>
    suspend fun getAllSkills(): SkillsDomainData
    suspend fun getSpecificSkill(skillId: Int): SkillData
}