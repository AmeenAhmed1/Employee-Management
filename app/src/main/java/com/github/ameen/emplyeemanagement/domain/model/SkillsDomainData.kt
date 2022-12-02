package com.github.ameen.emplyeemanagement.domain.model

import com.google.gson.annotations.SerializedName


data class SkillsDomainData(
    @SerializedName("skills")
    val data: List<SkillData>
)

data class SkillData(
    val skillId: Int?,
    val skillName: String,
)