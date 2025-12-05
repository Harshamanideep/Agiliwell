package com.app.agiliwell.ui.composables.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.agiliwell.R
import com.app.agiliwell.alarm.default_alarm.data.AlarmItem
import com.app.agiliwell.alarm.default_alarm.data.AgiliwellAlarmScheduler
import com.app.agiliwell.data.event.AgiliwellEvent
import com.app.agiliwell.data.event.NotificationEvent
import com.app.agiliwell.utils.AppUtils
import com.app.agiliwell.utils.TimeUtils
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAlarmList(
    modifier: Modifier = Modifier,
    allNotifications: List<AlarmItem>,
    agiliwellEventListener: (agiliwellEvent: AgiliwellEvent) -> Unit   // ✅ Fixed name here
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val scheduler = AgiliwellAlarmScheduler(context = context)
    val lazyListState = rememberLazyListState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var defaultTime by remember { mutableStateOf(LocalTime.now()) }
    val timeState = rememberTimePickerState(
        initialHour = defaultTime.hour,
        initialMinute = defaultTime.minute
    )

    var selectedNotification by remember {
        mutableStateOf(
            AlarmItem(
                time = LocalDateTime.now(),
                title = AppUtils.getRandomTitle(context),
                message = AppUtils.getRandomMessage(context)
            )
        )
    }

    var repeating by remember { mutableStateOf(true) }

    LaunchedEffect(allNotifications) {
        lazyListState.animateScrollToItem(0)
    }

    if (allNotifications.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .sizeIn(100.dp, 100.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.ic_outline_water_full),
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(12.dp),
                text = stringResource(R.string.no_custom_notification)
            )
        }
    } else {
        LazyColumn(state = lazyListState, modifier = modifier.padding(top = 10.dp)) {
            items(allNotifications, key = { alarm -> alarm.alarmId }) { alarm ->
                val time = TimeUtils.formatLocalDateTimeToTime(alarm.time)
                CustomNotificationItem(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    checked = alarm.alarmState,
                    onCheckChanged = { state ->
                        if (!state) {
                            scheduler.cancelCustomAlarm(alarm.alarmId)
                            agiliwellEventListener(
                                AgiliwellEvent.TriggerNotificationEvent(
                                    NotificationEvent.ToggleNotificationState(alarmId = alarm.alarmId, state = state)
                                )
                            )
                        } else {
                            if (repeating) scheduler.scheduleRepeating(alarm)
                            else scheduler.scheduleOneTime(alarm)

                            agiliwellEventListener(
                                AgiliwellEvent.TriggerNotificationEvent(
                                    NotificationEvent.ToggleNotificationState(alarmId = alarm.alarmId, state = true)
                                )
                            )
                        }
                    },
                    alarmRepeatable = if (alarm.repeating) {
                        repeating = true
                        "Repeating"
                    } else {
                        repeating = false
                        "Once"
                    },
                    time = time,
                    longClick = {
                        selectedNotification = alarm
                        defaultTime = LocalTime.of(alarm.time.hour, alarm.time.minute)
                        showBottomSheet = true
                    }
                )
            }
        }

        if (showBottomSheet) {
            AlarmBottomSheet(
                timeState = timeState,
                title = stringResource(R.string.edit_notification),
                onConfirm = { selectedTime ->
                    scheduler.cancelCustomAlarm(selectedNotification.alarmId)
                    selectedNotification = selectedNotification.copy(
                        time = LocalDateTime.of(LocalDate.now(), selectedTime),
                        repeating = repeating
                    )

                    Timber.d("Updating custom notification")
                    if (repeating) scheduler.scheduleRepeating(selectedNotification)
                    else scheduler.scheduleOneTime(selectedNotification)

                    agiliwellEventListener(
                        AgiliwellEvent.TriggerNotificationEvent(
                            NotificationEvent.UpdateNotification(selectedNotification)
                        )
                    )
                },
                showBottomSheet = { state -> showBottomSheet = state },
                repeatable = { value -> repeating = value },
                deleteListener = {
                    Timber.d("Cancelling and deleting notification")
                    showBottomSheet = false
                    scheduler.cancelCustomAlarm(selectedNotification.alarmId)
                    agiliwellEventListener(
                        AgiliwellEvent.TriggerNotificationEvent(
                            NotificationEvent.DeleteNotification(selectedNotification)
                        )
                    )
                }
            )
        }
    }
}
