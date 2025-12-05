package com.app.agiliwell.data.event

import com.app.agiliwell.data.model.Gender
import com.app.agiliwell.data.model.Units
import java.time.LocalTime


sealed class UserEvent {
    data object GetUserDetails : UserEvent()

    data class UpdateUserName(val name: String) : UserEvent()

    data class UpdateUserAge(val age: Int) : UserEvent()

    data class UpdateUserWeight(val weight: Double) : UserEvent()

    data class UpdateUserHeight(val height: Double) :UserEvent()

    data class UpdateUserGender(val gender: Gender) :UserEvent()

    data class UpdateUserSleepTime(val bedTime: LocalTime) : UserEvent()

    data class UpdateUserWakeUpTime(val wakeUpTime: LocalTime) : UserEvent()

    data class UpdateUserUnits(val unit: Units) : UserEvent()
}