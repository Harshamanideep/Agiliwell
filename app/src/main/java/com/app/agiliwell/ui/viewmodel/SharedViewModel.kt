package com.app.agiliwell.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.agiliwell.alarm.default_alarm.data.AlarmItem
import com.app.agiliwell.data.event.*
import com.app.agiliwell.data.model.*
import com.app.agiliwell.data.repository.AgiliwellRepository
import com.app.agiliwell.utils.Constants.BEVERAGE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: AgiliwellRepository
) : ViewModel() {

    // ---- Water Intake Data ---- //
    private val _todayAllIntakes = MutableStateFlow<List<Intake>>(emptyList())
    val todayAllIntakes: StateFlow<List<Intake>> = _todayAllIntakes

    private val _todayTotalIntake = MutableStateFlow(0)
    val todayTotalIntake: StateFlow<Int> = _todayTotalIntake

    private val _targetIntakeAmount = MutableStateFlow(0)
    val targetIntakeAmount: StateFlow<Int> = _targetIntakeAmount

    // ---- User ---- //
    private val _userDetails = MutableStateFlow(User())
    val userDetails: StateFlow<User> = _userDetails

    // ---- Notifications ---- //
    private val _allNotifications = MutableStateFlow<List<AlarmItem>>(emptyList())
    val allNotifications: StateFlow<List<AlarmItem>> = _allNotifications

    // ---- Yoga ---- //
    private val _allYogaSessions = MutableStateFlow<List<YogaSession>>(emptyList())
    val allYogaSessions: StateFlow<List<YogaSession>> = _allYogaSessions

    // ---- Exercise ---- //
    private val _allExercises = MutableStateFlow<List<Exercise>>(emptyList())
    val allExercises: StateFlow<List<Exercise>> = _allExercises

    // ---- Centralized Event Handler ---- //
    fun handleEvent(agiliwellEvent: AgiliwellEvent) {
        when (agiliwellEvent) {

            // ---- User ---- //
            is AgiliwellEvent.AddUser -> viewModelScope.launch {
                repository.addUser(agiliwellEvent.user)
            }

            is AgiliwellEvent.TriggerUserEvent -> handleUserEvent(agiliwellEvent.userEvent)

            // ---- Beverage ---- //
            is AgiliwellEvent.TriggerBeverageEvent -> handleBeverageEvent(agiliwellEvent.beverageEvent)

            // ---- Intake ---- //
            is AgiliwellEvent.TriggerIntakeEvent -> handleIntakeEvent(agiliwellEvent.intakeEvent)

            // ---- Notifications ---- //
            is AgiliwellEvent.TriggerNotificationEvent -> handleNotificationEvent(agiliwellEvent.notificationEvent)

            // ---- Yoga ---- //
            is AgiliwellEvent.TriggerYogaEvent -> handleYogaEvent(agiliwellEvent.yogaEvent)

            // ---- Exercise ---- //
            is AgiliwellEvent.TriggerExerciseEvent -> handleExerciseEvent(agiliwellEvent.exerciseEvent)
        }
    }

    // ------------------------------------------------------
    // 🧍 USER EVENT HANDLING
    // ------------------------------------------------------
    private fun handleUserEvent(event: UserEvent) {
        viewModelScope.launch {
            when (event) {
                UserEvent.GetUserDetails -> repository.getUserDetails().collect { _userDetails.value = it }
                is UserEvent.UpdateUserName -> repository.updateUserName(event.name)
                is UserEvent.UpdateUserAge -> repository.updateUserAge(event.age)
                is UserEvent.UpdateUserGender -> repository.updateUserGender(event.gender)
                is UserEvent.UpdateUserWeight -> repository.updateUserWeight(event.weight)
                is UserEvent.UpdateUserHeight -> repository.updateUserHeight(event.height)
                is UserEvent.UpdateUserUnits -> repository.updateUserUnits(event.unit)
                is UserEvent.UpdateUserSleepTime -> repository.updateUserSleepTime(event.bedTime)
                is UserEvent.UpdateUserWakeUpTime -> repository.updateUserWakeUpTime(event.wakeUpTime)
            }
        }
    }

    // ------------------------------------------------------
    // 💧 BEVERAGE EVENT HANDLING
    // ------------------------------------------------------
    private fun handleBeverageEvent(event: BeverageEvent) {
        viewModelScope.launch {
            when (event) {
                is BeverageEvent.AddBeverage -> repository.addBeverage(event.beverage)
                BeverageEvent.GetTargetAmount -> repository.getTargetAmount().collect {
                    _targetIntakeAmount.value = it
                }
                is BeverageEvent.UpdateTarget -> repository.updateIntakeTarget(event.target)
            }
        }
    }

    // ------------------------------------------------------
    // 🚰 INTAKE EVENT HANDLING
    // ------------------------------------------------------
    private fun handleIntakeEvent(event: IntakeEvent) {
        viewModelScope.launch {
            when (event) {
                is IntakeEvent.AddIntake -> repository.addIntake(event.intake)
                is IntakeEvent.DeleteIntake -> repository.deleteIntake(event.intake)
                is IntakeEvent.UpdateIntakeById ->
                    repository.updateIntakeById(event.intakeId, event.intakeAmount)

                is IntakeEvent.GetTodayIntake -> repository.getWaterIntakesForToday(
                    waterBeverageId = BEVERAGE_ID,
                    startDay = event.startDay,
                    endDay = event.endDay
                ).collect { _todayAllIntakes.value = it }

                is IntakeEvent.GetTodayTotalIntake -> repository.getTodayTotalIntake(
                    waterBeverageId = BEVERAGE_ID,
                    startDay = event.startDay,
                    endDay = event.endDay
                ).collect { _todayTotalIntake.value = it }
            }
        }
    }

    // ------------------------------------------------------
    // 🔔 NOTIFICATION EVENT HANDLING
    // ------------------------------------------------------
    private fun handleNotificationEvent(event: NotificationEvent) {
        viewModelScope.launch {
            when (event) {
                is NotificationEvent.SaveNotification -> repository.createAlarm(event.notification)
                NotificationEvent.GetAllScheduledNotifications ->
                    repository.getAllAlarms()?.collect { _allNotifications.value = it }

                is NotificationEvent.DeleteNotification -> repository.deleteAlarm(event.notification)
                is NotificationEvent.UpdateNotification -> repository.updateAlarm(event.notification)
                is NotificationEvent.ToggleNotificationState ->
                    repository.toggleAlarm(event.alarmId, event.state)
            }
        }
    }

    // ------------------------------------------------------
    // 🧘 YOGA EVENT HANDLING
    // ------------------------------------------------------
    private fun handleYogaEvent(event: YogaEvent) {
        viewModelScope.launch {
            when (event) {
                YogaEvent.GetAllYogaSessions ->
                    repository.getAllYogaSessions().collect { _allYogaSessions.value = it }

                is YogaEvent.AddYogaSession -> repository.addYogaSession(event.session)
                is YogaEvent.DeleteYogaSession -> repository.deleteYogaSession(event.session)
                is YogaEvent.UpdateYogaSession -> repository.updateYogaSession(event.session)
            }
        }
    }

    // ------------------------------------------------------
    // 🏋️ EXERCISE EVENT HANDLING
    // ------------------------------------------------------
    private fun handleExerciseEvent(event: ExerciseEvent) {
        viewModelScope.launch {
            when (event) {
                ExerciseEvent.GetAllExercises ->
                    repository.getAllExercises().collect { _allExercises.value = it }

                is ExerciseEvent.AddExercise -> repository.addExercise(event.exercise)
                is ExerciseEvent.DeleteExercise -> repository.deleteExercise(event.exercise)
                is ExerciseEvent.UpdateExercise -> repository.updateExercise(event.exercise)
            }
        }
    }
}
