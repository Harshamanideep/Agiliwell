package com.app.agiliwell.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.agiliwell.utils.Constants.YOGA_TABLE

@Entity(tableName = YOGA_TABLE)
data class YogaSession(
    @PrimaryKey(autoGenerate = true)
    val postureId: Long = 0,
    val name: String,
    val description: String,
    val benefits: String,
    val imageRes: Int // drawable resource ID
)
