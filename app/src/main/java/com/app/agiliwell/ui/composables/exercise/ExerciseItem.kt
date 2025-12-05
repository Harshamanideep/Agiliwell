package com.app.agiliwell.ui.composables.exercise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.agiliwell.data.model.Exercise

@Composable
fun ExerciseItem(exercise: Exercise, onClick: (Exercise) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick(exercise) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(exercise.name, style = MaterialTheme.typography.titleMedium)
            Text("Duration: ${exercise.durationMinutes} min", style = MaterialTheme.typography.bodyMedium)
            Text("Calories Burned: ${exercise.caloriesBurned}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
