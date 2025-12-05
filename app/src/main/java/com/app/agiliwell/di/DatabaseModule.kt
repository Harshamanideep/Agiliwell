package com.app.agiliwell.di

import android.content.Context
import androidx.room.Room
import com.app.agiliwell.data.AgiliwellDatabase
import com.app.agiliwell.utils.Constants.AGILIWELL_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context = context,
            AgiliwellDatabase::class.java,
            AGILIWELL_DATABASE_NAME
        ).addMigrations(AgiliwellDatabase.MIGRATION_1_2).build()

    @Provides
    @Singleton
    fun provideUserDao(database: AgiliwellDatabase) = database.userDao()

    @Provides
    @Singleton
    fun provideBeverageDao(database: AgiliwellDatabase) = database.beverageDao()

    @Provides
    @Singleton
    fun provideIntakeDao(database: AgiliwellDatabase) = database.intakeDao()

    @Provides
    @Singleton
    fun provideAlarmDao(database: AgiliwellDatabase) = database.alarmDao()

    @Provides
    @Singleton
    fun provideYogaDao(database: AgiliwellDatabase) = database.yogaDao()

    @Provides
    @Singleton
    fun provideExerciseDao(database: AgiliwellDatabase) = database.exerciseDao()

}