package com.app.agiliwell.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.agiliwell.utils.Constants.EXERCISE_TABLE

@Entity(tableName = EXERCISE_TABLE)
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Long = 0,
    val name: String,
    val durationMinutes: Int,
    val caloriesBurned: Int,
    val imageRes: Int
)
