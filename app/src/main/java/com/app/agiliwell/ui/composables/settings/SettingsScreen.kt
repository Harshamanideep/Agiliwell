package com.app.agiliwell.ui.composables.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.agiliwell.BuildConfig
import com.app.agiliwell.R
import com.app.agiliwell.data.event.BeverageEvent
import com.app.agiliwell.data.event.AgiliwellEvent
import com.app.agiliwell.data.event.UserEvent
import com.app.agiliwell.data.model.User
import com.app.agiliwell.ui.composables.SectionSpacer
import com.app.agiliwell.ui.composables.settings.items.AppVersionSettingItem
import com.app.agiliwell.ui.composables.settings.items.BedTime
import com.app.agiliwell.ui.composables.settings.items.Gender
import com.app.agiliwell.ui.composables.settings.items.Height
import com.app.agiliwell.ui.composables.settings.items.NameDisplay
import com.app.agiliwell.ui.composables.settings.items.PrivacyPolicy
import com.app.agiliwell.ui.composables.settings.items.Support
import com.app.agiliwell.ui.composables.settings.items.TargetAmount
import com.app.agiliwell.ui.composables.settings.items.Units
import com.app.agiliwell.ui.composables.settings.items.WakeUpTime
import com.app.agiliwell.ui.composables.settings.items.Weight
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    userDetails : User,
    waterDrinkTarget : Int,
    agiliwellEventListener: (agiliwellEvent:AgiliwellEvent) -> Unit,
    onBack : () -> Unit,
    onPrivacy:() -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings),
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
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(padding)
        ) {
            LaunchedEffect(Unit) {
                agiliwellEventListener(AgiliwellEvent.TriggerUserEvent(UserEvent.GetUserDetails))
            }

            Column {
                SectionSpacer(
                    modifier = Modifier.fillMaxWidth(),
                     title = stringResource(R.string.personal)
                )
                userDetails.name?.let {
                    NameDisplay(userName = it) { name ->
                        Timber.d("User updated name")
                        agiliwellEventListener(AgiliwellEvent.TriggerUserEvent(UserEvent.UpdateUserName(name)))
                    }
                }

                HorizontalDivider()

                Gender(userGender = userDetails.gender.genderValue) { gender ->
                    Timber.d("user updated gender")
                    agiliwellEventListener(AgiliwellEvent.TriggerUserEvent(UserEvent.UpdateUserGender(gender)))
                }

                HorizontalDivider()

                Height(userHeight = userDetails.height) { height ->
                    Timber.d("User updated height")
                    agiliwellEventListener(AgiliwellEvent.TriggerUserEvent(UserEvent.UpdateUserHeight(height = height)))
                }

                HorizontalDivider()

                Weight(userWeight = userDetails.weight, selectedUnits = userDetails.unit) { weight ->
                    Timber.d("User updated weight")
                    agiliwellEventListener(AgiliwellEvent.TriggerUserEvent(UserEvent.UpdateUserWeight(weight)))
                }

                HorizontalDivider()

                TargetAmount(targetAmount = waterDrinkTarget, selectedUnits = userDetails.unit ) {newTarget->
                    Timber.d("User updated drink target")
                    agiliwellEventListener(AgiliwellEvent.TriggerBeverageEvent(BeverageEvent.UpdateTarget(newTarget)))

                }

                HorizontalDivider()

                Units(userSelectedUnits = userDetails.unit.unitValue) {units->
                    Timber.d("User updated weight")
                    agiliwellEventListener(AgiliwellEvent.TriggerUserEvent(UserEvent.UpdateUserUnits(units)))
                }

                SectionSpacer(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.sleep_cycle)
                )

                userDetails.wakeUpTime?.let {
                    WakeUpTime(userWakeTime = it) { time->
                        agiliwellEventListener(AgiliwellEvent.TriggerUserEvent(UserEvent.UpdateUserWakeUpTime(time)))
                    }
                }

                HorizontalDivider()

                userDetails.bedTime?.let {
                    BedTime(userBedTime = it) { time->
                        agiliwellEventListener(AgiliwellEvent.TriggerUserEvent(UserEvent.UpdateUserSleepTime(time)))
                    }
                }

                SectionSpacer(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.others)
                )

                Support()

                HorizontalDivider()

                PrivacyPolicy {
                    onPrivacy()
                }

                HorizontalDivider()

                AppVersionSettingItem(
                    modifier = Modifier.fillMaxWidth(),
                    appVersion = BuildConfig.VERSION_NAME
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewSettingsScreen(){
    SettingsScreen(
        userDetails = User("Ashish"),
        waterDrinkTarget = 5000,
        agiliwellEventListener = {},
        onBack = { /*TODO*/ }) {
        
    }
}