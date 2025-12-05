package com.app.agiliwell.data.event

import com.app.agiliwell.data.model.User

sealed class AgiliwellEvent {
    class AddUser(val user: User) : AgiliwellEvent()

    data class TriggerIntakeEvent(val intakeEvent: IntakeEvent) : AgiliwellEvent()

    data class TriggerUserEvent(val userEvent: UserEvent) : AgiliwellEvent()

    data class TriggerBeverageEvent(val beverageEvent: BeverageEvent) : AgiliwellEvent()

    data class TriggerNotificationEvent(val notificationEvent: NotificationEvent) : AgiliwellEvent()

}