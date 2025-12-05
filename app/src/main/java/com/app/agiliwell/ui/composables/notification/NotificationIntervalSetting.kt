package com.app.agiliwell.ui.composables.notification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timelapse
import androidx.compose.material3.Icon
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
import com.app.agiliwell.ui.composables.notification.dialog.NotificationIntervalDialog
import com.app.agiliwell.ui.composables.settings.SettingItem

@Composable
fun NotificationIntervalSetting(
    modifier: Modifier = Modifier,
    notificationInterval: Double,
    newInterval : (interval:Double) -> Unit
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    SettingItem(modifier = modifier.clickable {
        showDialog = true
    }){
        Row(
            modifier = Modifier
                .semantics(mergeDescendants = true) {}
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                modifier= Modifier.padding(5.dp),
                imageVector = Icons.Outlined.Timelapse,
                contentDescription = null)
            Text(
                modifier = Modifier
                    .weight(1f).padding(start = 5.dp),
                fontSize = 18.sp,
                text = stringResource(R.string.notification_interval)
            )

            Text(
                fontSize = 18.sp,
                text = stringResource(R.string.hrs, notificationInterval)
            )
        }
    }
    if (showDialog) {
        NotificationIntervalDialog(
            interval = notificationInterval,
            showAlertDialog = { show -> showDialog = show },
            newValue ={interval ->
                newInterval(interval)
            } )
    }
}