package com.github.ameen.emplyeemanagement.data.mapper

import com.github.ameen.emplyeemanagement.data.local.entity.SkillsEntity
import com.github.ameen.emplyeemanagement.domain.model.SkillData

fun SkillsEntity.toDomain(): SkillData {
    return SkillData(
        skillId, skillName
    )
}

fun SkillData.toEntity(): SkillsEntity {
    return SkillsEntity(
        skillId, skillName
    )
}