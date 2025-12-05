package com.app.agiliwell.event

import android.content.Context
import com.app.agiliwell.alarm.default_alarm.data.AlarmDao
import com.app.agiliwell.alarm.default_alarm.data.AlarmItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class YogaEvent(private val alarmDao: AlarmDao) {

    
    suspend fun scheduleYogaSession(context: Context, dateTime: LocalDateTime) {
        withContext(Dispatchers.IO) {
            val alarm = AlarmItem(
                time = dateTime,
                title = "Yoga Time 🧘",
                message = "It’s time for your yoga session! Relax, breathe, and stretch your body.",
                interval = null,
                repeating = false,
                alarmState = true
            )

            alarmDao.upsert(alarm)
        }

        // Optional: show a local notification immediately
        YogaNotificationHelper.showYogaNotification(context)
    }
}
