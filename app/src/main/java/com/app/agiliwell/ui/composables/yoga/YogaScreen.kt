package com.app.agiliwell.ui.composables.yoga

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.agiliwell.data.model.YogaSession
import com.app.agiliwell.data.event.AgiliwellEvent

@Composable
fun YogaScreen(
    yogaSessions: List<YogaSession>,
    agiliwellEventListener: (AgiliwellEvent) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yoga Sessions") },
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
            items(yogaSessions) { session ->
                YogaItem(session) { selected ->
                    agiliwellEventListener(AgiliwellEvent.ShowYogaReminder(selected))
                }
            }
        }
    }
}
