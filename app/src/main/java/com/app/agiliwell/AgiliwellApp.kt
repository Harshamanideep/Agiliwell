package com.app.agiliwell

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.app.agiliwell.utils.Constants.CUSTOM_REMINDER_CHANNEL_ID
import com.app.agiliwell.utils.Constants.EXERCISE_REMINDER_CHANNEL_ID
import com.app.agiliwell.utils.Constants.WATER_REMINDER_CHANNEL_ID
import com.app.agiliwell.utils.Constants.YOGA_REMINDER_CHANNEL_ID
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AgiliwellApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        createNotificationChannels()
    }

    /**
     * Creates all required notification channels:
     * - Water reminders
     * - Custom reminders
     * - Yoga sessions
     * - Exercise activities
     */
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val waterChannel = NotificationChannel(
                WATER_REMINDER_CHANNEL_ID,
                getString(R.string.water_reminders),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = getString(R.string.water_reminder_description)
            }

            val customChannel = NotificationChannel(
                CUSTOM_REMINDER_CHANNEL_ID,
                getString(R.string.custom_reminders),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = getString(R.string.notification_description)
            }

            val yogaChannel = NotificationChannel(
                YOGA_REMINDER_CHANNEL_ID,
                getString(R.string.yoga_reminders),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = getString(R.string.yoga_reminder_description)
            }

            val exerciseChannel = NotificationChannel(
                EXERCISE_REMINDER_CHANNEL_ID,
                getString(R.string.exercise_reminders),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = getString(R.string.exercise_reminder_description)
            }

            notificationManager.createNotificationChannel(waterChannel)
            notificationManager.createNotificationChannel(customChannel)
            notificationManager.createNotificationChannel(yogaChannel)
            notificationManager.createNotificationChannel(exerciseChannel)
        }
    }
}
