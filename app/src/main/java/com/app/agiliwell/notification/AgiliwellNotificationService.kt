package com.app.agiliwell.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.app.agiliwell.AgiliwellActivity
import com.app.agiliwell.R
import com.app.agiliwell.utils.Constants.CUSTOM_REMINDER_CHANNEL_ID
import com.app.agiliwell.utils.Constants.WATER_REMINDER_CHANNEL_ID
import timber.log.Timber

class AgiliwellNotificationService(
    private val context: Context
) : AgiliwellNotification {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showNotification(notificationItem: NotificationItem) {
            Timber.d("Showing notification")
            val activityIntent = Intent(context, AgiliwellActivity::class.java)
            val activityPendingIntent = PendingIntent.getActivity(
                context,
                1,
                activityIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
            val notification = NotificationCompat.Builder(context, WATER_REMINDER_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_outline_water_bottle)
                .setContentTitle(notificationItem.title)
                .setContentText(notificationItem.message)
                .setContentIntent(activityPendingIntent)
                .build()

            notificationManager.notify(1, notification)
    }

    override fun showCustomNotification(notificationItem: NotificationItem) {
        Timber.d("Showing notification")
        val activityIntent = Intent(context, AgiliwellActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            2,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CUSTOM_REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_outline_water_bottle)
            .setContentTitle(notificationItem.title)
            .setContentText(notificationItem.message)
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(2, notification)
    }
}