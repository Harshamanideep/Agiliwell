package com.app.agiliwell.ui.composables.settings.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BubbleChart
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.agiliwell.R
import com.app.agiliwell.data.model.Units
import com.app.agiliwell.ui.composables.settings.SettingItem

@Composable
fun Units(
    modifier: Modifier = Modifier,
    userSelectedUnits:String,
    newUnits: (value:Units) -> Unit
) {
    Box {
        var expanded by remember { mutableStateOf(false) }
        SettingItem(modifier = modifier.clickable {
            expanded = true
        }) {
            Row(
                modifier = Modifier
                    .semantics(mergeDescendants = true) {}
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(end = 5.dp),
                    imageVector = Icons.Rounded.BubbleChart,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier
                        .weight(1f).padding(start = 5.dp),
                    fontSize = 18.sp,
                    text = stringResource(R.string.units)
                )

                Text(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    text = userSelectedUnits
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            for (unit in Units.entries) {
                DropdownMenuItem(
                    text = { Text(text = unit.unitValue) },
                    onClick = {
                        newUnits(unit)
                        expanded = false
                    },
                    Modifier.offset(16.dp, 0.dp)
                )
            }
        }
    }
}