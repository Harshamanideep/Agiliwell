package com.app.agiliwell.ui.screens.yoga

import androidx.compose.foundation.Image
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

data class YogaPose(val name: String, val icon: Int)

@Composable
fun YogaScreen(navController: NavController) {
    val yogaList = listOf(
        YogaPose("Surya Namaskar", R.drawable.ic_yoga_pose),
        YogaPose("Tree Pose", R.drawable.ic_meditation),
        YogaPose("Cobra Pose", R.drawable.ic_stretch),
        YogaPose("Lotus Pose", R.drawable.ic_meditation),
        YogaPose("Mountain Pose", R.drawable.ic_yoga_pose),
        YogaPose("Bridge Pose", R.drawable.ic_stretch)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Yoga Sessions 🧘‍♀️",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(yogaList) { pose ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Future: Open Pose Details */ }
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = pose.icon),
                        contentDescription = pose.name,
                        modifier = Modifier.size(60.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(pose.name, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("set_yoga_alarm") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Set Yoga Reminder")
        }
    }
}
