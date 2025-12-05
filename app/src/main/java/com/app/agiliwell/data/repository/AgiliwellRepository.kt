package com.app.agiliwell.data.repository

import com.app.agiliwell.alarm.default_alarm.data.AlarmDao
import com.app.agiliwell.alarm.default_alarm.data.AlarmItem
import com.app.agiliwell.data.dao.BeverageDao
import com.app.agiliwell.data.dao.IntakeDao
import com.app.agiliwell.data.dao.UserDao
import com.app.agiliwell.data.model.Beverage
import com.app.agiliwell.data.model.Gender
import com.app.agiliwell.data.model.Intake
import com.app.agiliwell.data.model.Units
import com.app.agiliwell.data.model.User
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@ViewModelScoped
class AgiliwellRepository @Inject constructor(
    private val userDao: UserDao,
    private val intakeDao: IntakeDao,
    private val beverageDao: BeverageDao,
    private val alarmDao: AlarmDao,
    private val yogaDao: YogaDao,
    private val exerciseDao: ExerciseDao
    
) {
    // ---- User ---- //
    fun getUserDetails(): Flow<User>{
        return userDao.getUserDetails()
    }

    suspend fun addUser(user: User) {
        userDao.addUser(user = user)
    }

    suspend fun updateUserName(name:String){
        userDao.updateUserName(name)
    }

    suspend fun updateUserAge(age: Int) {
        userDao.updateUserAge(age)
    }

    suspend fun updateUserWeight(weight: Double) {
        userDao.updateUserWeight(weight)
    }

    suspend fun updateUserHeight(height: Double) {
        userDao.updateUserHeight(height)
    }

    suspend fun updateUserGender(gender: Gender) {
        userDao.updateUserGender(gender)
    }

    suspend fun updateUserSleepTime(bedTime: LocalTime) {
        userDao.updateUserSleepTime(bedTime)
    }

    suspend fun updateUserWakeUpTime(wakeUpTime: LocalTime) {
        userDao.updateUserWakeUpTime(wakeUpTime)
    }

    suspend fun updateUserUnits(unit: Units) {
        userDao.updateUserUnits(unit)
    }


    suspend fun updateUser(user: User) {
        userDao.updateUser(user = user)
    }

    // ---- Beverage ---- //
    suspend fun addBeverage(beverage: Beverage) {
        beverageDao.insertBeverage(beverage = beverage)
    }

    fun getTargetAmount(): Flow<Int>{
        return beverageDao.getTotalIntakeAmount()
    }

    suspend fun updateIntakeTarget(target:Int){
        beverageDao.updateTotalIntakeAmount(newTotalIntakeAmount = target)
    }

    // ---- Intake ---- //
    suspend fun addIntake(intake: Intake) {
        intakeDao.insertIntake(intake = intake)
    }

    suspend fun deleteIntake(intake: Intake) {
        intakeDao.deleteIntake(intake = intake)
    }

    suspend fun updateIntake(intake: Intake) {
        intakeDao.updateIntake(intake = intake)
    }

    suspend fun updateIntakeById(intakeId: Long, intakeAmount: Int){
        intakeDao.updateIntakeAmountById(intakeId = intakeId, intakeAmount = intakeAmount)
    }

    fun getWaterIntakesForToday(
        waterBeverageId: Long,
        startDay: LocalDateTime,
        endDay: LocalDateTime
    ): Flow<List<Intake>> {
        return intakeDao.getWaterIntakesForToday(
            waterBeverageId = waterBeverageId,
            startOfDay = startDay,
            startOfNextDay = endDay
        )
    }

    fun getTodayTotalIntake(waterBeverageId: Long, startDay: LocalDateTime,
                                    endDay: LocalDateTime): Flow<Int>{
        return intakeDao.getTotalWaterIntakeForToday(waterBeverageId = waterBeverageId,
            startDate = startDay,
            endDate = endDay)
    }

    // ---- Alarm ---- //
    suspend fun createAlarm(alarm : AlarmItem){
        return alarmDao.insertAlarm(alarmItem = alarm)
    }

    fun getAllAlarms(): Flow<List<AlarmItem>>? {
        return alarmDao.getAllAlarms()
    }

    suspend fun deleteAlarm(alarm : AlarmItem){
        return alarmDao.deleteAlarm(alarm)
    }

    suspend fun updateAlarm(alarm: AlarmItem){
        return alarmDao.updateAlarm(alarm)
    }

    suspend fun toggleAlarm(alarmId:Long, state:Boolean){
        return alarmDao.toggleAlarmState(alarmId = alarmId, state = state)
    }


     // ---- Yoga ---- //
     
    suspend fun addYogaSession(session: YogaSession) {
        yogaDao.insert(session)
    }

    fun getAllYogaSessions(): Flow<List<YogaSession>> {
        return yogaDao.getAllSessions()
    }

    // ---- Exercise ---- //
    suspend fun addExerciseSession(session: ExerciseSession) {
        exerciseDao.insert(session)
    }

    fun getAllExerciseSessions(): Flow<List<ExerciseSession>> {
        return exerciseDao.getAllSessions()
    }
}
