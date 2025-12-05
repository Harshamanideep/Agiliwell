package com.app.agiliwell.ui.composables.userdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.agiliwell.R
import com.app.agiliwell.data.model.Units
import com.app.agiliwell.ui.composables.settings.SettingItem

@Composable
fun UnitItem(
    modifier: Modifier = Modifier,
    title: String,
    selectedUnit: Units,
    onOptionSelected: (option: Units) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    SettingItem(modifier = modifier) {
        Row(
            modifier = Modifier
                .clickable(
                    onClick = { expanded = !expanded },
                    onClickLabel = stringResource(R.string.select_unit)
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(2f),
                fontSize = 18.sp,
                text = title
            )
            Text(text = selectedUnit.unitValue)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            Units.entries.forEach { units ->
                DropdownMenuItem(
                    text = { Text(text = units.unitValue) },
                    onClick = {
                        onOptionSelected(units)
                        expanded = false
                    },
                    Modifier.offset(16.dp, 0.dp)
                )
            }
        }
    }

}