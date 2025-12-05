package com.app.agiliwell.ui.composables.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.agiliwell.R
import com.app.agiliwell.alarm.default_alarm.data.AlarmItem
import com.app.agiliwell.alarm.default_alarm.data.AgiliwellAlarmScheduler
import com.app.agiliwell.data.event.AgiliwellEvent
import com.app.agiliwell.data.event.NotificationEvent
import com.app.agiliwell.ui.composables.SectionSpacer
import com.app.agiliwell.ui.composables.notification.dialog.AlertDialogNotification
import com.app.agiliwell.utils.AppUtils
import com.app.agiliwell.utils.PreferencesManager
import timber.log.Timber
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    notificationList: List<AlarmItem>,
    agiliwellEventListener: (event: AgiliwellEvent) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scheduler = AgiliwellAlarmScheduler(context = context)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var showDialog by remember { mutableStateOf(false) }
    var switchState by remember {
        mutableStateOf(PreferencesManager(context).getNotificationPreference())
    }
    var intervalTime by remember {
        mutableStateOf(
            PreferencesManager(context = context)
                .getNotificationInterval()
        )
    }

    var showBottomSheet by remember { mutableStateOf(false) }
    val timeState = rememberTimePickerState(
        initialHour = 5,
        initialMinute = 50
    )
    var repeatable by remember {
        mutableStateOf(false)
    }
    var selectedDateTime = LocalDateTime.now()

    LaunchedEffect(Unit) {
        agiliwellEventListener(AgiliwellEvent.TriggerNotificationEvent(NotificationEvent.GetAllScheduledNotifications))
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.notification),
                        modifier = Modifier.padding(start = 10.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.go_back)
                        )

                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(10.dp),
                onClick = { showBottomSheet = true },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            NotificationSetting(
                modifier = Modifier
                    .verticalScroll(
                        rememberScrollState()
                    ),
                title = stringResource(R.string.setting_enable_notifications),
                checked = switchState,
                onCheckChanged = { checked ->
                    if (!checked) {
                        Timber.d("Showing cancel notification dialog")
                        showDialog = true
                    } else {
                        Timber.d("Turning back notification on")
                        PreferencesManager(context).saveNotificationPreference(true)
                        val alarmItem = AlarmItem(
                            LocalDateTime.now().plusMinutes(30),
                            PreferencesManager(context = context)
                                .getNotificationInterval(),
                            AppUtils.getRandomTitle(context),
                            AppUtils.getRandomMessage(context)
                        )
                        scheduler.scheduleRegular(alarmItem)
                        switchState = true
                    }
                }
            )

            HorizontalDivider()

            NotificationIntervalSetting(
                notificationInterval = intervalTime
            ) { interval ->
                Timber.d("New interval is %.2f", interval)
                val scheduler = AgiliwellAlarmScheduler(context = context)
                scheduler.cancel()
                intervalTime = interval
                PreferencesManager(context = context).setNotificationInterval(interval)
                val alarmItem = AlarmItem(
                    LocalDateTime.now().plusMinutes(30),
                    interval,
                    AppUtils.getRandomTitle(context),
                    AppUtils.getRandomMessage(context)
                )
                scheduler.scheduleRegular(alarmItem)
            }

            SectionSpacer(
                modifier = Modifier.fillMaxWidth(),
                title = "Custom notification"
            )

            CustomAlarmList(
                allNotifications = notificationList,
                agiliwellEventListener = agiliwellEventListener
            )

            if (showBottomSheet) {
                AlarmBottomSheet(
                    timeState = timeState,
                    title = "New Notification",
                    onConfirm = { time ->
                        val alarm = AlarmItem(
                            time = selectedDateTime.with(time),
                            title = AppUtils.getRandomTitle(context),
                            message = AppUtils.getRandomMessage(context),
                            repeating = repeatable
                        )
                        agiliwellEventListener(
                            AgiliwellEvent.TriggerNotificationEvent(
                                NotificationEvent.SaveNotification(alarm)
                            )
                        )
                        val latestNotificationItem = notificationList.lastOrNull()
                        Timber.d("Setting custom notification")
                        if (latestNotificationItem != null) {
                            if (repeatable) {
                                scheduler.scheduleRepeating(latestNotificationItem)

                            } else {
                                scheduler.scheduleOneTime(latestNotificationItem)
                            }
                        }
                    },
                    showBottomSheet = { state ->
                        showBottomSheet = state
                    },
                    repeatable = { repeating ->
                        repeatable = repeating
                    }
                )
            }

            if (showDialog) {
                AlertDialogNotification(
                    onDismissRequest = {
                        Timber.d("Dismissing the notification dialog")
                        showDialog = false
                    },
                    onConfirmation = {
                        showDialog = false
                        switchState = false
                        Timber.d("User turned off Notifications")
                        PreferencesManager(context).saveNotificationPreference(false)
                        AgiliwellAlarmScheduler(context = context).cancel()
                    },
                    dialogTitle = stringResource(R.string.disable_notifications),
                    dialogText = stringResource(R.string.notification_disable_message),
                    icon = Icons.Default.Warning
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewNotificationScreen() {
    NotificationScreen(notificationList = emptyList(), agiliwellEventListener = {

    }) {

    }
}