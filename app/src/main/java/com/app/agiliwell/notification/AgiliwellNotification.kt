package com.app.agiliwell.notification

interface AgiliwellNotification {
    fun showNotification(notificationItem: NotificationItem)
    fun showCustomNotification(notificationItem: NotificationItem)
}