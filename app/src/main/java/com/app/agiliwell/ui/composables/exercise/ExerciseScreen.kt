package com.app.agiliwell.ui.composables.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.agiliwell.data.model.Exercise
import com.app.agiliwell.data.event.AgiliwellEvent

@Composable
fun ExerciseScreen(
    exercises: List<Exercise>,
    agiliwellEventListener: (AgiliwellEvent) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exercises") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(exercises) { exercise ->
                ExerciseItem(exercise) { selected ->
                    agiliwellEventListener(AgiliwellEvent.ShowExerciseReminder(selected))
                }
            }
        }
    }
}
