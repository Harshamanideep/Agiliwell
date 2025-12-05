package com.app.agiliwell.alarm.default_alarm.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.agiliwell.utils.Constants.ALARM_DATABASE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Query("SELECT * FROM $ALARM_DATABASE_TABLE")
    fun getAllAlarms(): Flow<List<AlarmItem>>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlarm(alarmItem: AlarmItem)

    @Update
    suspend fun updateAlarm(alarmItem: AlarmItem)

    @Delete
    suspend fun deleteAlarm(alarmItem: AlarmItem)

    @Query("UPDATE $ALARM_DATABASE_TABLE SET alarmState = :state WHERE alarmId = :alarmId")
    suspend fun toggleAlarmState(alarmId: Long, state: Boolean)

     @Query("SELECT * FROM $ALARM_DATABASE_TABLE WHERE title LIKE '%Yoga%'")
    fun getYogaAlarms(): Flow<List<AlarmItem>>

    @Query("SELECT * FROM $ALARM_DATABASE_TABLE WHERE title LIKE '%Exercise%'")
    fun getExerciseAlarms(): Flow<List<AlarmItem>>

    @Query("DELETE FROM $ALARM_DATABASE_TABLE WHERE title LIKE '%Yoga%'")
    suspend fun deleteAllYogaAlarms()

    @Query("DELETE FROM $ALARM_DATABASE_TABLE WHERE title LIKE '%Exercise%'")
    suspend fun deleteAllExerciseAlarms()
}