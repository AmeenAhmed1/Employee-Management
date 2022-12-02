package com.github.ameen.emplyeemanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.ameen.emplyeemanagement.data.local.entity.SkillsEntity

@Dao
interface SkillsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkills(skillsData: List<SkillsEntity>): List<Long>

    @Query("SELECT * FROM SkillsTable WHERE skillId =:skillId")
    suspend fun getSpecificSkill(skillId: Int): SkillsEntity

    @Query("SELECT * FROM SkillsTable")
    suspend fun getAllSkills(): List<SkillsEntity>

}