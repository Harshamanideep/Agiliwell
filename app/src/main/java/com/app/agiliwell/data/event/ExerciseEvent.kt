package com.app.agiliwell.event

import android.content.Context
import com.app.agiliwell.alarm.default_alarm.data.AlarmDao
import com.app.agiliwell.alarm.default_alarm.data.AlarmItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class ExerciseEvent(private val alarmDao: AlarmDao) {

    
    suspend fun scheduleExerciseReminder(context: Context, dateTime: LocalDateTime) {
        withContext(Dispatchers.IO) {
            val alarm = AlarmItem(
                time = dateTime,
                title = "Exercise Reminder 💪",
                message = "Get ready to move! It’s time for your workout session.",
                interval = null,
                repeating = false,
                alarmState = true
            )

            alarmDao.upsert(alarm)
        }

        // Optional: show a local notification immediately
        ExerciseNotificationHelper.showExerciseNotification(context)
    }
}
