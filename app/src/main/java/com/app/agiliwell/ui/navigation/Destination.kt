package com.app.agiliwell.ui.navigation

sealed class Destination(val path: String) {
    data object HomeScreen : Destination("home")

    data object UserDetails : Destination("userDetails")

    data object WaterDetails : Destination("waterDetails")

    data object Settings : Destination("settings")

    data object Notification : Destination("notifications")

    data object Privacy : Destination("privacy")

    data object Onboarding : Destination("onboarding")
    
    data object Yoga : Destination("yoga")
    
    data object Exercise : Destination("exercise")

    companion object {
        fun fromString(route: String): Destination {
            return when (route) {
                Settings.path -> Settings
                UserDetails.path -> UserDetails
                WaterDetails.path -> WaterDetails
                Yoga.path -> Yoga
                Exercise.path -> Exercise
                else -> HomeScreen
            }

        }
    }
}