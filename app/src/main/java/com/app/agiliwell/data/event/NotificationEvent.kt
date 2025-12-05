package com.app.agiliwell.data.event

import com.app.agiliwell.alarm.default_alarm.data.AlarmItem

sealed class NotificationEvent {
    data class SaveNotification(val notification: AlarmItem): NotificationEvent()

    data class UpdateNotification(val notification: AlarmItem): NotificationEvent()

    data class DeleteNotification(val notification: AlarmItem): NotificationEvent()

    data object GetAllScheduledNotifications: NotificationEvent()

    data class ToggleNotificationState(val alarmId:Long, val state:Boolean): NotificationEvent()
}