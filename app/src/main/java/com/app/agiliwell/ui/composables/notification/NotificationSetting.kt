package com.app.agiliwell.ui.composables.notification

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.agiliwell.R
import com.app.agiliwell.ui.composables.settings.SettingItem

@Composable
fun NotificationSetting(
    modifier: Modifier = Modifier,
    title:String,
    checked:Boolean,
    onCheckChanged: (checked:Boolean) -> Unit
){
    val notificationsEnabledState = if (checked) {
        stringResource(R.string.cd_notifications_enabled)
    } else stringResource(R.string.cd_notifications_disabled)

    SettingItem(modifier=modifier) {
        Row(
            modifier = Modifier
                .toggleable(
                    value = checked,
                    onValueChange = onCheckChanged,
                    role = Role.Switch
                )
                .semantics {
                    stateDescription = notificationsEnabledState
                }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier= Modifier.padding(5.dp),
                imageVector = Icons.Outlined.Campaign,
                contentDescription = null)
            Text(
                text = title,
                modifier = Modifier
                    .weight(1f).padding(start = 5.dp),
                fontSize = 18.sp
            )
            Switch(checked = checked, onCheckedChange = null)
        }
    }
}

@Composable
@Preview
fun NotificationSettingsPreview(){
    NotificationSetting( title = "Enable Notification", checked = false, onCheckChanged = { })
}