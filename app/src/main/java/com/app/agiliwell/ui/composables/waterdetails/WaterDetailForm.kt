package com.app.agiliwell.ui.composables.waterdetails

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.app.agiliwell.R
import com.app.agiliwell.alarm.default_alarm.data.AlarmItem
import com.app.agiliwell.alarm.default_alarm.data.AgiliwellAlarmScheduler
import com.app.agiliwell.data.event.BeverageEvent
import com.app.agiliwell.data.event.AgiliwellEvent
import com.app.agiliwell.data.model.Beverage
import com.app.agiliwell.data.model.User
import com.app.agiliwell.ui.composables.notification.dialog.NotificationDialog
import com.app.agiliwell.ui.composables.userdetails.DetailTextField
import com.app.agiliwell.utils.AppUtils
import com.app.agiliwell.utils.Constants.USER_ID
import com.app.agiliwell.utils.Converters
import com.app.agiliwell.utils.PreferencesManager
import timber.log.Timber
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterDetailForm(
    onProceed: () -> Unit,
    userDetails: User,
    agiliwellEventListener: (agiliwellEvent: AgiliwellEvent) -> Unit,
) {
    val context = LocalContext.current
    var waterIntakeAmount by remember {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.enter_details))
                }
            )
        }
    ) {

        var notificationTriggered by remember {
            mutableStateOf(false)
        }
        var notificationPermissionGranted by remember {
            mutableStateOf(false)
        }

        val notificationInterval =1.0
        PreferencesManager(context).setNotificationInterval(notificationInterval)

        if (notificationTriggered && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            NotificationDialog { value ->
                if (value) {
                    Timber.d("Creating notification")
                    val scheduler = AgiliwellAlarmScheduler(context = context)
                    val alarmItem = AlarmItem(
                        LocalDateTime.now().plusHours(1),
                        notificationInterval,
                        AppUtils.getRandomTitle(context),
                        AppUtils.getRandomMessage(context)
                    )
                    scheduler.scheduleRegular(alarmItem)
                    notificationPermissionGranted= true
                    onProceed()
                } else {
                    onProceed()
                }
            }
        }else if (notificationTriggered){
            Timber.d("Creating notification")
            val scheduler = AgiliwellAlarmScheduler(context = context)
            val alarmItem = AlarmItem(
                LocalDateTime.now().plusHours(1),
                1.0,
                AppUtils.getRandomTitle(context),
                AppUtils.getRandomMessage(context)
            )
            scheduler.scheduleRegular(alarmItem)
            notificationPermissionGranted = true
            onProceed()
        }
        PreferencesManager(context).saveNotificationPreference(notificationPermissionGranted)

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            HorizontalDivider()

            OutlinedCard(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {
                Icon(
                    modifier = Modifier.sizeIn(150.dp, 150.dp),
                    imageVector = Icons.Rounded.WaterDrop, contentDescription = null,
                )
            }

            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 18.sp,
                    text = stringResource(R.string.total_daily_water_intake)
                )

                DetailTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = waterIntakeAmount,
                    onValueChange = { newValue ->
                        if (newValue.isDigitsOnly() && newValue.isNotBlank()) {
                            waterIntakeAmount = newValue
                        } else if (newValue.isEmpty()) {
                            waterIntakeAmount = ""
                        }
                    },
                    label = stringResource(R.string.water_amount, Converters.getUnitName(
                        userDetails.unit,
                        1
                    )),
                    placeholder = stringResource(R.string.enter_your_water_intake,
                        Converters.getUnitName(
                            userDetails.unit,
                        1
                    )),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    )

                )

                Spacer(modifier = Modifier.weight(1f))

                var submitButtonEnabled by remember {
                    mutableStateOf(false)
                }
                submitButtonEnabled = waterIntakeAmount.isNotEmpty()
                val beverageName = stringResource(id = R.string.water)
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    enabled = submitButtonEnabled,
                    onClick = {
                        agiliwellEventListener(AgiliwellEvent.TriggerBeverageEvent(BeverageEvent.AddBeverage(
                            Beverage(
                                userId = USER_ID,
                                beverageName = beverageName,
                                totalIntakeAmount = waterIntakeAmount.toInt()
                            )
                        )))
                        notificationTriggered = true
                  //      onProceed()
                    }) {
                    Text(text = stringResource(id = R.string.proceed))

                }
            }
        }

    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWaterDetailForm() {
    WaterDetailForm(onProceed = {  }, agiliwellEventListener = {}, userDetails = User("ashish"))
}
