package com.app.agiliwell.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A flexible section header with optional background shading and divider.
 * Used to visually separate app sections like "Yoga", "Exercise", or "Hydration Goals".
 */
@Composable
fun SectionSpacer(
    modifier: Modifier = Modifier,
    title: String? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f),
    showDivider: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth()) {

        // Optional title with subtle background highlight
        title?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor)
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }

        // Optional divider line for better visual separation
        if (showDivider) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                thickness = 1.dp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSectionSpacer() {
    Column {
        SectionSpacer(title = "Yoga Sessions")
        SectionSpacer(title = "Exercise Activities")
        SectionSpacer(title = "Hydration Tracker")
    }
}
