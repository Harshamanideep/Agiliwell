package com.app.agiliwell

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.app.agiliwell.data.event.EventScheduler
import com.app.agiliwell.ui.navigation.Navigation
import com.app.agiliwell.ui.theme.AgiliwellTheme
import com.app.agiliwell.ui.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import com.app.agiliwell.alarm.default_alarm.data.AlarmDao

@AndroidEntryPoint
class AgiliwellActivity : ComponentActivity() {

    // Inject AlarmDao for scheduling events
    @Inject
    lateinit var alarmDao: AlarmDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Optional: schedule daily Yoga & Exercise reminders (first launch or demo)
        lifecycleScope.launch {
            try {
                val eventScheduler = EventScheduler(alarmDao)
                val now = LocalDateTime.now()

                // Schedule yoga reminder at 6 AM
                val yogaTime = now.withHour(6).withMinute(0).withSecond(0)
                eventScheduler.scheduleYoga(this@AgiliwellActivity, yogaTime)

                // Schedule exercise reminder at 7 PM
                val exerciseTime = now.withHour(19).withMinute(0).withSecond(0)
                eventScheduler.scheduleExercise(this@AgiliwellActivity, exerciseTime)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        setContent {
            AgiliwellTheme {
                val navController = rememberNavController()
                val viewModel: SharedViewModel = viewModel()

                Navigation(
                    navController = navController,
                    sharedViewModel = viewModel
                )
            }
        }
    }
}
