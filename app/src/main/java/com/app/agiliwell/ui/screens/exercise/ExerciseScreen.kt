package com.app.agiliwell.ui.screens.exercise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.agiliwell.R

data class Exercise(val name: String, val icon: Int)

@Composable
fun ExerciseScreen(navController: NavController) {
    val exerciseList = listOf(
        Exercise("Push Ups", R.drawable.ic_exercise),
        Exercise("Jumping Jacks", R.drawable.ic_running),
        Exercise("Squats", R.drawable.ic_exercise),
        Exercise("Running", R.drawable.ic_running),
        Exercise("Stretching", R.drawable.ic_stretch),
        Exercise("Plank", R.drawable.ic_exercise)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Exercise Activities 💪",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(exerciseList) { exercise ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Future: Open Exercise Details */ }
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = exercise.icon),
                        contentDescription = exercise.name,
                        modifier = Modifier.size(60.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(exercise.name, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("set_exercise_alarm") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Set Exercise Reminder")
        }
    }
}
