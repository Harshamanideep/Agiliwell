package com.app.agiliwell.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.app.agiliwell.utils.Constants.INTAKE_DATABASE_TABLE
import java.time.LocalDateTime

@Entity(tableName = INTAKE_DATABASE_TABLE,
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["userId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Beverage::class, parentColumns = ["beverageId"], childColumns = ["beverageId"], onDelete = ForeignKey.CASCADE)
    ]
)
data class Intake(
    val userId: Long, // Foreign key reference to User
    val beverageId: Long, // Foreign key reference to Beverage
    val intakeAmount: Int,
    val intakeDateTime: LocalDateTime, // Using LocalDateTime for simplicity
    @PrimaryKey(autoGenerate = true)
    val intakeId: Long = 0,
)
