package com.app.agiliwell.ui.composables.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.agiliwell.data.model.Intake
import com.app.agiliwell.data.model.User
import com.app.agiliwell.data.event.AgiliwellEvent
import com.app.agiliwell.ui.composables.SectionSpacer
import com.app.agiliwell.ui.composables.home.components.WaterIntakeCard

@Composable
fun Home(
    agiliwellEventListener: (AgiliwellEvent) -> Unit,
    todayIntake: Int,
    targetIntake: Int,
    intakeList: List<Intake>,
    userDetails: User,
    navigateToSettings: () -> Unit,
    navigateToNotifications: () -> Unit,
    navigateToYoga: () -> Unit,
    navigateToExercise: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Header
        Text(
            text = "Welcome, ${userDetails.name.ifEmpty { "User" }} 👋",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Hydration tracker section
        SectionSpacer(title = "Hydration Tracker")
        WaterIntakeCard(
            todayIntake = todayIntake,
            targetIntake = targetIntake,
            onAddIntake = { /* open add intake dialog */ }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Yoga section
        SectionSpacer(title = "Yoga Sessions")
        ActionCard(
            title = "Start Yoga Practice",
            subtitle = "Explore calming yoga postures",
            onClick = navigateToYoga
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Exercise section
        SectionSpacer(title = "Exercise Activities")
        ActionCard(
            title = "Go for a Workout",
            subtitle = "Plan your daily fitness routine",
            onClick = navigateToExercise
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Settings and Notification buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = navigateToNotifications) {
                Text("Notifications")
            }
            Button(onClick = navigateToSettings) {
                Text("Settings")
            }
        }
    }
}

/**
 * Reusable Action Card for Yoga / Exercise navigation
 */
@Composable
fun ActionCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
            )
        }
    }
}
