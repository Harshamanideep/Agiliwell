package com.app.agiliwell.alarm.default_alarm

import com.app.agiliwell.alarm.default_alarm.data.AlarmItem

interface AlarmScheduler {
    fun scheduleRegular(item: AlarmItem)
    fun scheduleOneTime(item: AlarmItem)
    fun scheduleRepeating(item: AlarmItem)
    fun cancel()
    fun cancelCustomAlarm(alarmId: Long)
    fun scheduleYogaReminder(item: AlarmItem)
    fun scheduleExerciseReminder(item: AlarmItem)
}