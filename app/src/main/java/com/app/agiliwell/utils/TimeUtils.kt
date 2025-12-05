package com.app.agiliwell.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object TimeUtils {
    fun formatLocalDateTimeToTime(dateTime: LocalDateTime): String {
        val time = dateTime.toLocalTime()
        val formattedTime = DateTimeFormatter.ofPattern("hh:mm a").format(time)
        return formattedTime
    }

    fun formatTime(hour: Int, minute: Int, isAm: Boolean): String {
        val formattedHour = if (hour > 12) hour - 12 else hour
        val amPm = if (hour >= 12) "PM" else "AM"
        return String.format(Locale.getDefault(), "%02d:%02d %s", formattedHour, minute, if (isAm) "AM" else "PM")
    }

}