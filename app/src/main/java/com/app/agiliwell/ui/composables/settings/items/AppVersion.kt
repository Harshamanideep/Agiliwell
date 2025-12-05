package com.app.agiliwell.ui.composables.settings.items

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.agiliwell.R
import com.app.agiliwell.ui.composables.settings.SettingItem

@Composable
fun AppVersionSettingItem(
    modifier: Modifier = Modifier,
    appVersion:String
){
    SettingItem(modifier= modifier) {
        Row(
            modifier = Modifier
                .semantics(mergeDescendants = true){}
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                modifier = Modifier.padding(end = 5.dp),
                imageVector = Icons.Rounded.Info,
                contentDescription = null
            )
            Text(
                modifier = Modifier.weight(1f).padding(start = 5.dp),
                fontSize = 18.sp,
                text = stringResource(id = R.string.setting_app_version_title))

            Text(
                fontSize = 18.sp,
                text = appVersion)
        }
    }
}