package com.app.agiliwell.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.agiliwell.data.model.Gender
import com.app.agiliwell.data.model.Units
import com.app.agiliwell.data.model.User
import com.app.agiliwell.utils.Constants
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime

@Dao
interface UserDao {
    @Query("SELECT * FROM ${Constants.USER_DATABASE_TABLE}")
    suspend fun getUser(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM ${Constants.USER_DATABASE_TABLE}")
    fun getUserDetails() : Flow<User>

    @Query("UPDATE ${Constants.USER_DATABASE_TABLE} SET name = :name")
    suspend fun updateUserName(name: String)

    @Query("UPDATE ${Constants.USER_DATABASE_TABLE} SET age = :age")
    suspend fun updateUserAge(age: Int)

    @Query("UPDATE ${Constants.USER_DATABASE_TABLE} SET weight = :weight")
    suspend fun updateUserWeight(weight: Double)

    @Query("UPDATE ${Constants.USER_DATABASE_TABLE} SET height = :height")
    suspend fun updateUserHeight(height: Double)

    @Query("UPDATE ${Constants.USER_DATABASE_TABLE} SET gender = :gender")
    suspend fun updateUserGender(gender: Gender)

    @Query("UPDATE ${Constants.USER_DATABASE_TABLE} SET bedTime = :bedTime")
    suspend fun updateUserSleepTime(bedTime: LocalTime)

    @Query("UPDATE ${Constants.USER_DATABASE_TABLE} SET wakeUpTime = :wakeUpTime")
    suspend fun updateUserWakeUpTime(wakeUpTime: LocalTime)

    @Query("UPDATE ${Constants.USER_DATABASE_TABLE} SET unit = :unit")
    suspend fun updateUserUnits(unit: Units)
}
