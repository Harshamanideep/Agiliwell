package com.app.agiliwell.ui.composables.settings.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Wc
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
import com.app.agiliwell.data.model.Gender
import com.app.agiliwell.ui.composables.settings.SettingItem

@Composable
fun Gender(
    modifier: Modifier = Modifier,
    userGender: String,
    newValue: (name: Gender) -> Unit,
) {
    Box {
        var expanded by remember { mutableStateOf(false) }
        SettingItem(modifier = modifier.clickable {
            expanded = !expanded
        }) {
            Row(
                modifier = Modifier
                    .semantics(mergeDescendants = true) {}
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(end = 5.dp),
                    imageVector = Icons.Rounded.Wc,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(1f),
                    fontSize = 18.sp,
                    text = stringResource(R.string.gender)
                )

                Text(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    text = userGender
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            for (gender in Gender.entries) {
                DropdownMenuItem(
                    text = { Text(text = gender.genderValue) },
                    onClick = {
                        newValue(gender)
                        expanded = false
                    },
                    Modifier.offset(16.dp, 0.dp)
                )
            }
        }
    }
}

