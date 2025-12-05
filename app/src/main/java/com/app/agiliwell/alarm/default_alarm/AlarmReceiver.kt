package com.app.agiliwell.alarm.default_alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.agiliwell.alarm.default_alarm.data.AlarmDao
import com.app.agiliwell.notification.AgiliwellNotificationService
import com.app.agiliwell.notification.NotificationItem
import com.app.agiliwell.utils.AppUtils
import com.app.agiliwell.utils.PreferencesManager
import com.app.agiliwell.utils.SleepCycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmDao: AlarmDao

    override fun onReceive(context: Context, intent: Intent?) {
        Timber.d("onReceive:: received alarm broadcast")

        if (intent == null) {
            Timber.e("Intent is null — cannot proceed")
            return
        }

        val notificationType = intent.getStringExtra("NOTIFICATION_TYPE") ?: "regular"
        val alarmId = intent.getLongExtra("ALARM_ID", -1L)
        val category = intent.getStringExtra("CATEGORY") ?: "General"

        Timber.d("Notification type: $notificationType, Alarm ID: $alarmId, Category: $category")

        val prefs = PreferencesManager(context)
        val currentTime = LocalTime.now(ZoneId.systemDefault()).truncatedTo(ChronoUnit.MINUTES)
        val userSleepTime = prefs.getSleepCycleTime(SleepCycle.SLEEP_TIME)
        val userWakeTime = prefs.getSleepCycleTime(SleepCycle.WAKE_TIME)

        // Skip sending notifications when user is asleep
        if (currentTime.isAfter(userSleepTime) || currentTime.isBefore(userWakeTime)) {
            Timber.d("Skipping notification — user is likely asleep")
            return
        }

        val notificationService = AgiliwellNotificationService(context)

        when (category) {
            "Yoga" -> {
                Timber.d("Triggering Yoga reminder notification")
                notificationService.showCustomNotification(
                    NotificationItem(
                        title = "🧘 Time for Yoga",
                        message = "Breathe deeply and start your posture session."
                    )
                )
            }

            "Exercise" -> {
                Timber.d("Triggering Exercise reminder notification")
                notificationService.showCustomNotification(
                    NotificationItem(
                        title = "🏋️ Exercise Time!",
                        message = "Let’s move! Start your workout session now."
                    )
                )
            }

            else -> {
                // Default alarm logic (water intake or general reminders)
                val notificationItem = NotificationItem(
                    title = AppUtils.getRandomTitle(context),
                    message = AppUtils.getRandomMessage(context)
                )

                when (notificationType) {
                    "regular" -> {
                        Timber.d("Triggering regular reminder notification")
                        notificationService.showNotification(notificationItem)
                    }

                    "custom" -> {
                        Timber.d("Triggering custom reminder notification")
                        if (alarmId >= 0) {
                            CoroutineScope(Dispatchers.IO).launch {
                                Timber.d("Toggling alarm state OFF for ID: $alarmId")
                                alarmDao.toggleAlarmState(alarmId, false)
                            }
                        }
                        notificationService.showCustomNotification(notificationItem)
                    }

                    else -> {
                        Timber.w("Unknown notification type: $notificationType")
                    }
                }
            }
        }
    }
}
