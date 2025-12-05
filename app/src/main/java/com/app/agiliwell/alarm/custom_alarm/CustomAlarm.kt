package com.app.agiliwell.alarm.custom_alarm

import com.app.agiliwell.alarm.default_alarm.data.AlarmItem

interface CustomAlarm {
    fun schedule(item: AlarmItem)
    fun cancel()
}
