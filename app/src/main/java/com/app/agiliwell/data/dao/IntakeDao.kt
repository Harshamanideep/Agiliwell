package com.app.agiliwell.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.agiliwell.data.model.Intake
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface IntakeDao {

    // Method to insert a new water intake entry
    @Insert
    suspend fun insertIntake(intake: Intake)

    // Method to delete a water intake entry
    @Delete
    suspend fun deleteIntake(intake: Intake)

    // Method to update a water intake entry
    @Update
    suspend fun updateIntake(intake: Intake)

    // Method to get water intake entries for today
    @Query("SELECT * FROM intake WHERE beverageId = :waterBeverageId AND intakeDateTime >= :startOfDay AND intakeDateTime < :startOfNextDay ORDER BY intakeId DESC")
    fun getWaterIntakesForToday(waterBeverageId: Long, startOfDay: LocalDateTime, startOfNextDay: LocalDateTime): Flow<List<Intake>>


    // Method to get water intake entries for a specific date range
    @Query("SELECT * FROM intake WHERE beverageId = :waterBeverageId AND DATE(intakeDateTime) BETWEEN DATE(:startDate) AND DATE(:endDate)")
    fun getWaterIntakesForDateRange(waterBeverageId: Long, startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<Intake>>

    // Method to get the total amount of water intake for today
    @Query("SELECT SUM(intakeAmount) FROM intake WHERE beverageId = :waterBeverageId AND intakeDateTime >= :startDate AND intakeDateTime < :endDate ")
    fun getTotalWaterIntakeForToday(waterBeverageId: Long, startDate: LocalDateTime, endDate: LocalDateTime): Flow<Int>

    // Method to get monthly average intake amount for a specific month
    @Query("SELECT AVG(intakeAmount) FROM intake WHERE beverageId = :beverageId AND strftime('%Y-%m', intakeDateTime) = :month")
    suspend fun getMonthlyAverageIntake(beverageId: Long, month: String): Double?

    // Method to get weekly average intake amount for a specific week
    @Query("SELECT AVG(intakeAmount) FROM intake WHERE beverageId = :beverageId AND strftime('%W', intakeDateTime) = :week")
    suspend fun getWeeklyAverageIntake(beverageId: Long, week: String): Double?

    @Query("UPDATE intake SET intakeAmount = :intakeAmount WHERE intakeId = :intakeId")
    suspend fun updateIntakeAmountById(intakeId: Long, intakeAmount: Int)

}
