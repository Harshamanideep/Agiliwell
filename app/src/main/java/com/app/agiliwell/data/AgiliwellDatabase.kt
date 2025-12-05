package com.app.agiliwell.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.agiliwell.alarm.default_alarm.data.AlarmDao
import com.app.agiliwell.alarm.default_alarm.data.AlarmItem
import com.app.agiliwell.data.dao.BeverageDao
import com.app.agiliwell.data.dao.IntakeDao
import com.app.agiliwell.data.dao.UserDao
import com.app.agiliwell.data.dao.YogaDao
import com.app.agiliwell.data.dao.ExerciseDao
import com.app.agiliwell.data.model.Beverage
import com.app.agiliwell.data.model.Intake
import com.app.agiliwell.data.model.User
import com.app.agiliwell.data.model.YogaSession   
import com.app.agiliwell.data.model.Exercise
import com.app.agiliwell.utils.Constants.ALARM_DATABASE_TABLE
import com.app.agiliwell.utils.Converters

@Database(
    entities = [
        Beverage::class,
        User::class,
        Intake::class,
        AlarmItem::class,
        YogaSession::class,
        Exercise::class
    ],
    version = 3, // 🔹 incremented since new tables added
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AgiliwellDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun intakeDao(): IntakeDao
    abstract fun beverageDao(): BeverageDao
    abstract fun alarmDao(): AlarmDao

    // 🔹 Newly added DAOs
    abstract fun yogaDao(): YogaDao
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("DROP TABLE IF EXISTS $ALARM_DATABASE_TABLE")

                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS $ALARM_DATABASE_TABLE (" +
                            "alarmId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            "time INTEGER NOT NULL," +
                            "interval REAL," +
                            "title TEXT NOT NULL," +
                            "message TEXT NOT NULL," +
                            "repeating INTEGER NOT NULL DEFAULT 0," +
                            "alarmState INTEGER NOT NULL DEFAULT 1)"
                )
            }
        }

        // 🔹 Updated migration for YogaSession and Exercise tables
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS yoga_session_table (" +
                            "sessionId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            "name TEXT NOT NULL," +
                            "description TEXT NOT NULL," +
                            "benefits TEXT NOT NULL," +
                            "imageRes INTEGER NOT NULL)"
                )

                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS exercise_table (" +
                            "exerciseId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            "name TEXT NOT NULL," +
                            "durationMinutes INTEGER NOT NULL," +
                            "caloriesBurned INTEGER NOT NULL," +
                            "imageRes INTEGER NOT NULL)"
                )
            }
        }
    }
}
