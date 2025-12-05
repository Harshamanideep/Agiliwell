package com.app.agiliwell.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.agiliwell.utils.Constants.USER_DATABASE_TABLE
import java.time.LocalTime

@Entity(tableName = USER_DATABASE_TABLE)
data class User(
    // No id field
    val name: String?= null,
    val age: Int? = null,
    val gender: Gender = Gender.FEMALE,
    val weight: Double = 0.0,
    val height: Double = 0.0,
    val bedTime: LocalTime?= null,
    val wakeUpTime: LocalTime?=null,
    val unit: Units = Units.KG_ML,
    @PrimaryKey
    val id: Long = 100
)