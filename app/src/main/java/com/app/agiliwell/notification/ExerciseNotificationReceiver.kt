package com.app.agiliwell.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.agiliwell.R

class ExerciseNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notification = NotificationCompat.Builder(context, "agiliwell_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("🏋️ Exercise Time!")
            .setContentText("Stay active — it’s time for your exercise session!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(2002, notification)
    }
}
