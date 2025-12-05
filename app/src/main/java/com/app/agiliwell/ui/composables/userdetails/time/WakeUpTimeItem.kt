package com.app.agiliwell.ui.composables.userdetails.time

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.agiliwell.R
import com.app.agiliwell.ui.composables.settings.SettingItem

@Composable
fun WakeUpTimeItem(
    modifier: Modifier = Modifier,
    title: String,
    setTime : String,
    onSettingClicked: () -> Unit,
) {
    SettingItem(modifier = modifier) {
        Row(
            modifier = Modifier.run {
                clickable(
                            onClickLabel = stringResource(R.string.select_wake_up_time)
                        ) {
                            onSettingClicked()
                        }
                        .padding(horizontal = 16.dp)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .weight(1f),
                fontSize = 18.sp
            )
            Text(
                text = setTime,
                fontSize = 18.sp
            )
        }
    }
}