package com.app.agiliwell.utils

import android.content.Context
import com.app.agiliwell.R

object AppUtils {
    val Context.isFreshInstall get() = with(packageManager.getPackageInfo(packageName, 0)) {
        firstInstallTime == lastUpdateTime
    }

    fun getRandomTitle(context: Context): String {
        val titles = context.resources.getStringArray(R.array.water_reminder_titles)
        val randomIndex = titles.indices.random()
        return titles[randomIndex]
    }

    fun getRandomMessage(context: Context): String {
        val messages = context.resources.getStringArray(R.array.water_reminder_messages)
        val randomIndex = messages.indices.random()
        return messages[randomIndex]
    }
}