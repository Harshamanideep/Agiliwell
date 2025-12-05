package com.app.agiliwell.data.event

import com.app.agiliwell.data.model.Intake
import java.time.LocalDateTime

sealed class IntakeEvent {

    data class AddIntake(val intake:Intake) : IntakeEvent()

    data class GetTodayIntake(val startDay: LocalDateTime, val endDay:LocalDateTime): IntakeEvent()

    data class GetTodayTotalIntake(val startDay: LocalDateTime, val endDay:LocalDateTime): IntakeEvent()

    data class DeleteIntake(val intake: Intake):IntakeEvent()

    data class UpdateIntakeById(val intakeId:Long, val intakeAmount:Int) : IntakeEvent()

    // TODO: add them
//    data object DeleteIntake:IntakeEvent()
//    data object UpdateIntake:IntakeEvent()
}