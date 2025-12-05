package com.app.agiliwell.ui.composables.yoga

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.agiliwell.data.model.YogaSession

@Composable
fun YogaItem(session: YogaSession, onClick: (YogaSession) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick(session) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(session.name, style = MaterialTheme.typography.titleMedium)
            Text(session.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(4.dp))
            Text("Benefits: ${session.benefits}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
