package com.app.agiliwell.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.app.agiliwell.utils.PreferencesManager
import timber.log.Timber
import java.time.LocalDateTime

class WaterNotificationReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Timber.d("WaterNotificationReceiver triggered")

        if (intent == null) {
            Timber.e("Intent is null — cannot handle action")
            return
        }

        val action = intent.action
        val prefs = PreferencesManager(context)

        when (action) {
            ACTION_WATER_INTAKE -> {
                // Increment water count in Preferences
                val currentAmount = prefs.getWaterIntakeAmount()
                prefs.setWaterIntakeAmount(currentAmount + 250) // +250ml per press

                // Optionally: show a toast or notification update
                Toast.makeText(context, "Nice! +250ml added 💧", Toast.LENGTH_SHORT).show()

                Timber.i("Water intake updated: ${currentAmount + 250}ml at ${LocalDateTime.now()}")
            }

            else -> {
                Timber.w("Unknown action: $action")
            }
        }
    }

    companion object {
        const val ACTION_WATER_INTAKE = "com.app.agiliwell.ACTION_WATER_INTAKE"
    }
}