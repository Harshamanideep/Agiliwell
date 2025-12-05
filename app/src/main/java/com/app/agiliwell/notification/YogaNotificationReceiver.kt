package com.app.agiliwell.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.agiliwell.R

class YogaNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notification = NotificationCompat.Builder(context, "agiliwell_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("🧘 Time for Yoga!")
            .setContentText("Take a few minutes to stretch and breathe mindfully.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(2001, notification)
    }
}
