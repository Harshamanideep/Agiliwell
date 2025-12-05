package com.app.agiliwell.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.app.agiliwell.utils.Constants.BEVERAGE_DATABASE_TABLE

@Entity(tableName = BEVERAGE_DATABASE_TABLE,
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["userId"], onDelete = ForeignKey.CASCADE),
    ])
data class Beverage(
    val userId:Long,
    val beverageName: String,
    val totalIntakeAmount : Int,
    @PrimaryKey
    val beverageId: Long = 101,
)