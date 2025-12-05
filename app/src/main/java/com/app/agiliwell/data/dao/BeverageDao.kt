package com.app.agiliwell.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.agiliwell.data.model.Beverage
import com.app.agiliwell.utils.Constants.BEVERAGE_DATABASE_TABLE
import com.app.agiliwell.utils.Constants.BEVERAGE_ID
import kotlinx.coroutines.flow.Flow

@Dao
interface BeverageDao {
    @Insert
    suspend fun insertBeverage(beverage: Beverage)

    @Query("SELECT totalIntakeAmount FROM $BEVERAGE_DATABASE_TABLE")
    fun getTotalIntakeAmount(): Flow<Int>

    @Query("UPDATE $BEVERAGE_DATABASE_TABLE SET totalIntakeAmount = :newTotalIntakeAmount WHERE beverageId = $BEVERAGE_ID")
    suspend fun updateTotalIntakeAmount(newTotalIntakeAmount: Int)
}