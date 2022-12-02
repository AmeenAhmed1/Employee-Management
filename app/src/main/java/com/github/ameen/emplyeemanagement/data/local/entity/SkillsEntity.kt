package com.github.ameen.emplyeemanagement.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SkillsTable")
data class SkillsEntity(
    @PrimaryKey(autoGenerate = true)
    val skillId: Int?,
    val skillName: String,
)
