package com.app.agiliwell.ui.composables.home.water

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.agiliwell.R
import com.app.agiliwell.data.event.IntakeEvent
import com.app.agiliwell.data.event.AgiliwellEvent
import com.app.agiliwell.data.model.Intake
import com.app.agiliwell.data.model.Units
import com.app.agiliwell.ui.composables.home.alertdialog.AmountEditDialog
import com.app.agiliwell.utils.Converters
import com.app.agiliwell.utils.TimeUtils.formatLocalDateTimeToTime
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun RecordList(
    modifier: Modifier = Modifier,
    todayAllIntakes: List<Intake>,
    selectedUnits: Units,
    agiliwellEventListener: (AgiliwellEvent) -> Unit
) {
    // Trigger initial event to load today's intakes
    LaunchedEffect(Unit) {
        val startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
        val startOfNextDay = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIN)
        agiliwellEventListener(
            AgiliwellEvent.TriggerIntakeEvent(
                IntakeEvent.GetTodayIntake(
                    startDay = startOfDay,
                    endDay = startOfNextDay
                )
            )
        )
    }

    val lazyListState = rememberLazyListState()
    LaunchedEffect(todayAllIntakes) {
        lazyListState.animateScrollToItem(0)
    }

    var selectedIntakeId by remember { mutableLongStateOf(0L) }
    var intakeAmount by remember { mutableIntStateOf(0) }
    val showDialog = remember { mutableStateOf(false) }

    if (todayAllIntakes.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .sizeIn(50.dp, 50.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.ic_rounded_glass_cup),
                contentDescription = null
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.no_water_consumed)
            )
        }
    } else {
        LazyColumn(state = lazyListState, modifier = modifier) {
            items(todayAllIntakes, key = { it.intakeId }) { intake ->
                val time = formatLocalDateTimeToTime(intake.intakeDateTime)
                intakeAmount = intake.intakeAmount

                WaterRecordItem(
                    handleDelete = {
                        agiliwellEventListener(
                            AgiliwellEvent.TriggerIntakeEvent(
                                IntakeEvent.DeleteIntake(intake)
                            )
                        )
                    },
                    handleEdit = {
                        Timber.d("Edit water amount dialog shown")
                        selectedIntakeId = intake.intakeId
                        showDialog.value = true
                    },
                    waterIntakeTime = time,
                    waterIntakeAmount = "${intake.intakeAmount} ${Converters.getUnitName(selectedUnits, 1)}"
                )
            }
        }

        if (showDialog.value) {
            AmountEditDialog(
                setShowDialog = { show ->
                    showDialog.value = show
                },
                onDismissRequest = { newValue ->
                    Timber.d("Updated selected item water amount")
                    agiliwellEventListener(
                        AgiliwellEvent.TriggerIntakeEvent(
                            IntakeEvent.UpdateIntakeById(
                                intakeId = selectedIntakeId,
                                intakeAmount = newValue
                            )
                        )
                    )
                },
                currentValue = intakeAmount
            )
        }
    }
}
