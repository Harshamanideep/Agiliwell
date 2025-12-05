package com.app.agiliwell.ui.composables.notification.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.app.agiliwell.R
import com.app.agiliwell.ui.composables.userdetails.DetailTextField

@Composable
fun NotificationIntervalDialog(
    modifier: Modifier = Modifier,
    interval: Double,
    newValue: (interval: Double) -> Unit,
    showAlertDialog: (show: Boolean) -> Unit,
) {
    var notificationIntevals by remember { mutableStateOf(interval.toString()) }

    Dialog(onDismissRequest = {
        // Do nothing
    }) {
        Card(modifier = modifier) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = stringResource(R.string.change_notification_interval),
                    fontSize = 20.sp
                )
                Text(
                    modifier = Modifier.padding(10.dp),
                    fontSize = 16.sp,
                    text = stringResource(R.string.custom_interval_message)
                )
                DetailTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = notificationIntevals,
                    onValueChange = { newValue ->
                        if (newValue.isNotEmpty()) {
                            notificationIntevals = newValue
                        } else if (newValue.isEmpty()) {
                            notificationIntevals = ""
                        }
                    },
                    label = "Interval in Hrs",
                    placeholder =  stringResource(R.string.change_notification_interval),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Schedule,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        if (notificationIntevals.isNotBlank()) {
                            IconButton(onClick = { notificationIntevals = "" }) {
                                Icon(
                                    imageVector = Icons.Outlined.Clear,
                                    contentDescription = stringResource(R.string.clear)
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { showAlertDialog(false) },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(stringResource(R.string.dismiss))
                    }
                    TextButton(
                        onClick = {
                            newValue(notificationIntevals.toDouble())
                            showAlertDialog(false)
                        },
                        modifier = Modifier.padding(8.dp),
                        enabled = notificationIntevals.isNotBlank()
                    ) {
                        Text(stringResource(R.string.confirm))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIntervalSetting(){
   NotificationIntervalDialog(interval = 1.2, newValue = {}) {

    }
}