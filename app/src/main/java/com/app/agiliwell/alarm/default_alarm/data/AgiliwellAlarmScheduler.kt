package com.app.agiliwell.alarm.default_alarm.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.app.agiliwell.alarm.default_alarm.AlarmReceiver
import com.app.agiliwell.notification.YogaNotificationReceiver
import com.app.agiliwell.notification.ExerciseNotificationReceiver
import timber.log.Timber
import java.time.ZoneId

class AgiliwellAlarmScheduler(
    val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun scheduleRegular(item: AlarmItem) {
        Timber.d("Scheduling regular alarm")
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("NOTIFICATION_TYPE", "regular")
        }

        if (item.interval != null) {
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                (item.interval * 60 * 60 * 1000).toLong(),
                PendingIntent.getBroadcast(
                    context,
                    101,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
        }
    }

    override fun scheduleOneTime(item: AlarmItem) {
        Timber.d("Scheduling custom one-time alarm")
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("NOTIFICATION_TYPE", "custom")
            putExtra("ALARM_ID", item.alarmId)
        }

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                context,
                item.alarmId.toInt(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun scheduleRepeating(item: AlarmItem) {
        Timber.d("Scheduling daily repeat alarm")
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("NOTIFICATION_TYPE", "custom")
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            (24 * 60 * 60 * 1000).toLong(),
            PendingIntent.getBroadcast(
                context,
                item.alarmId.toInt(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    fun scheduleYogaReminder(timeInMillis: Long) {
        Timber.d("Scheduling Yoga reminder")
        val intent = Intent(context, YogaNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            2001,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
    }

    fun scheduleExerciseReminder(timeInMillis: Long) {
        Timber.d("Scheduling Exercise reminder")
        val intent = Intent(context, ExerciseNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            2002,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
    }

    override fun cancel() {
        Timber.d("Cancelling all alarms")
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                101,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancelCustomAlarm(alarmId: Long) {
        Timber.d("Cancelling custom alarm with ID: $alarmId")
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmId.toInt(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    fun cancelYogaReminder() {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                2001,
                Intent(context, YogaNotificationReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    fun cancelExerciseReminder() {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                2002,
                Intent(context, ExerciseNotificationReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}
