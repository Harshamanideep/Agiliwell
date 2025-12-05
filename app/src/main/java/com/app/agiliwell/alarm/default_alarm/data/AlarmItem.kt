package com.app.agiliwell.alarm.default_alarm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.agiliwell.utils.Constants.ALARM_DATABASE_TABLE
import java.time.LocalDateTime

@Entity(tableName = ALARM_DATABASE_TABLE)
data class AlarmItem (
    val time: LocalDateTime,
    val interval:Double? = null,
    val title:String,
    val message:String,
    val repeating: Boolean = false,
    val alarmState: Boolean = true,
    val category: String = "General",
    @PrimaryKey(autoGenerate = true)
    val alarmId:Long = 0L
)