package com.app.agiliwell.ui.composables.settings.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
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
import com.app.agiliwell.ui.composables.settings.SettingItem
import com.app.agiliwell.ui.composables.timepicker.TimeDialog
import com.app.agiliwell.utils.TimeUtils.formatTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WakeUpTime(
    modifier: Modifier = Modifier,
    userWakeTime:LocalTime,
    onTimeSelected: (time: LocalTime) -> Unit,
) {
    var showTimeDialog by remember {
        mutableStateOf(false)
    }
    val defaultSelectedTime by remember { mutableStateOf(userWakeTime) }
    val timeState = rememberTimePickerState(
        initialHour = defaultSelectedTime.hour,
        initialMinute = defaultSelectedTime.minute
    )

    val formattedTime = remember(timeState.hour, timeState.minute) {
        formatTime(timeState.hour, timeState.minute, timeState.hour < 12)
    }
    SettingItem(modifier = modifier .clickable {
        showTimeDialog = true
    }) {
        Row(
            modifier = Modifier
                .semantics(mergeDescendants = true) {}
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(end = 5.dp),
                imageVector = Icons.Rounded.WbSunny,
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .weight(1f).padding(start = 5.dp),
                fontSize = 18.sp,
                text = stringResource(R.string.wake_up_time)
            )

            Text(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp,
                text = formattedTime
            )
        }
    }

        TimeDialog(
            showDialog = showTimeDialog,
            onDismissRequest = { showTimeDialog = false },
            onConfirm = { selectedTime ->
                showTimeDialog = false
                onTimeSelected(selectedTime)
            },
            timeState = timeState
        )

}