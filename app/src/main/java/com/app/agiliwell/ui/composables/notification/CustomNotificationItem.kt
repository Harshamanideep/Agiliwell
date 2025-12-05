package com.app.agiliwell.ui.composables.notification

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.agiliwell.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomNotificationItem(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckChanged: (checked: Boolean) -> Unit,
    longClick: () -> Unit,
    alarmRepeatable: String,
    time: String
) {
    OutlinedCard(
        modifier = modifier.combinedClickable(
            onLongClick = {
                longClick()
            },
            onClick = {

            }
        )
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .weight(0.1f)
                    .sizeIn(minWidth = 40.dp, minHeight = 40.dp),
                painter = painterResource(id = R.drawable.ic_rounded_alarm_on),
                contentDescription = null
            )
            Spacer(Modifier.size(16.dp))
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    fontSize = 20.sp,
                    text = time,
                    style = TextStyle(fontWeight = FontWeight(600))
                )

                Text(
                    text = alarmRepeatable
                )
            }
            Spacer(Modifier.size(16.dp))
            Switch(checked = checked, onCheckedChange = onCheckChanged)
        }
    }
}

@Preview
@Composable
fun CustomNotificationItemPreview() {
    CustomNotificationItem(
        checked = false,
        time = "12:02 AM",
        alarmRepeatable = "Repeat",
        onCheckChanged = {},
        longClick = {}
    )
}