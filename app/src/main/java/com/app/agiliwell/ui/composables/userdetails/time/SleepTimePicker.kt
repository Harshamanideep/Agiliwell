package com.app.agiliwell.ui.composables.userdetails.time

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.app.agiliwell.R
import com.app.agiliwell.ui.composables.timepicker.TimeDialog
import com.app.agiliwell.utils.TimeUtils.formatTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepTimePicker(
    onTimeSelected: (time: LocalTime) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var timeSelected by remember { mutableStateOf(false) }

    val defaultSelectedTime by remember { mutableStateOf(LocalTime.of(23, 0)) }
    val timeState = rememberTimePickerState(
        initialHour = defaultSelectedTime.hour,
        initialMinute = defaultSelectedTime.minute
    )
    val formattedTime = remember(timeState.hour, timeState.minute) {
        formatTime(timeState.hour, timeState.minute, timeState.hour < 12)
    }
    if (!showDialog && !timeSelected) {
        onTimeSelected(defaultSelectedTime)
    }

    TimeDialog(
        showDialog = showDialog,
        onDismissRequest = { showDialog = false },
        onConfirm = { selectedTime ->
            showDialog = false
            onTimeSelected(selectedTime)
        },
        timeState = timeState
    )

    SleepTimeItem(
        title = stringResource(id = R.string.sleep_time),
        setTime = formattedTime,
        onSettingClicked = {
            timeSelected=true
            showDialog = true
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ShowSleepDialog() {
    SleepTimePicker(onTimeSelected = {
    })
}