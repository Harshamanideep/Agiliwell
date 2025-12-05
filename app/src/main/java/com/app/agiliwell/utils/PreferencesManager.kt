package com.app.agiliwell.utils

import android.content.Context
import android.content.SharedPreferences
import com.app.agiliwell.data.model.Units
import java.time.LocalTime

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AgiliwellPrefs", Context.MODE_PRIVATE)

    fun saveFirstLaunchStatus(isFirstLaunch: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("firstLaunch", isFirstLaunch)
        editor.apply()
    }

    fun isFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean("firstLaunch", true)
    }

    fun saveOnboarding() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("onboarded", true)
        editor.apply()
    }

    fun isOnboarded(defaultValue:Boolean): Boolean {
        return sharedPreferences.getBoolean("onboarded", defaultValue)
    }

    fun saveSleepCycleTime(key: SleepCycle, localTime: LocalTime) {
        val editor = sharedPreferences.edit()
        editor.putString(key.value, localTime.toString())
        editor.apply()
    }

    fun getSleepCycleTime(key: SleepCycle): LocalTime {
        val timeString = sharedPreferences.getString(key.value, null)
        return if (timeString != null) {
            LocalTime.parse(timeString)
        } else {
            LocalTime.now()
        }
    }

    fun saveWaterDetails() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("water_details_filled", true)
        editor.apply()
    }

    fun isWaterDetailsFilled(): Boolean {
        return sharedPreferences.getBoolean("water_details_filled", false)
    }

    fun saveUserDetails() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("user_details_filled", true)
        editor.apply()
    }

    fun isUserDetailsFilled(): Boolean {
        return sharedPreferences.getBoolean("user_details_filled", false)
    }

    fun setWaterIntakeAmount(value:Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("selected_water_amount", value)
        editor.apply()
    }

    fun getWaterIntakeAmount(): Int {
        return sharedPreferences.getInt("selected_water_amount", 100)
    }

    fun saveNotificationPreference(value:Boolean){
        val editor = sharedPreferences.edit()
        editor.putBoolean("notification_pref", value)
        editor.apply()
    }

    fun getNotificationPreference():Boolean{
        return sharedPreferences.getBoolean("notification_pref", false)
    }

    fun setNotificationInterval(interval:Double){
        val editor = sharedPreferences.edit()
        editor.putDouble("notification_interval", interval)
        editor.apply()
    }

    fun getNotificationInterval():Double{
        return sharedPreferences.getDouble("notification_interval",1.0)
    }

    private fun SharedPreferences.Editor.putDouble(key: String, double: Double) =
        putLong(key, java.lang.Double.doubleToRawLongBits(double))

    private fun SharedPreferences.getDouble(key: String, default: Double) =
        java.lang.Double.longBitsToDouble(getLong(key, java.lang.Double.doubleToRawLongBits(default)))

}

enum class SleepCycle(val value : String) {
    SLEEP_TIME("sleep_time"),
    WAKE_TIME("wake_time")
}