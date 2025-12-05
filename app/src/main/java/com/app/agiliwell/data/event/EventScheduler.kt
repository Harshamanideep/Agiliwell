package com.app.agiliwell.event

import android.content.Context
import com.app.agiliwell.alarm.default_alarm.data.AlarmDao
import java.time.LocalDateTime

class EventScheduler(private val alarmDao: AlarmDao) {

    val eventScheduler = EventScheduler(alarmDao)
    eventScheduler.scheduleYoga(context, LocalDateTime.now().plusMinutes(5))

    private val yogaEvent = YogaEvent(alarmDao)
    private val exerciseEvent = ExerciseEvent(alarmDao)

    suspend fun scheduleYoga(context: Context, dateTime: LocalDateTime) {
        yogaEvent.scheduleYogaSession(context, dateTime = dateTime)
    }

    suspend fun scheduleExercise(context: Context, dateTime: LocalDateTime) {
        exerciseEvent.scheduleExerciseReminder(context, dateTime = dateTime)
    }

    suspend fun cancelAll() {
        // implement mass cancel if needed
    }
}
