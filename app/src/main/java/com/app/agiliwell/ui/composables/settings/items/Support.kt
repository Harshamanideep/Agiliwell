package com.app.agiliwell.ui.composables.settings.items

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.Recommend
import androidx.compose.material.icons.rounded.Support
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.agiliwell.R
import com.app.agiliwell.ui.composables.settings.SettingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Support(
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val supportMeIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/sri-vaishnavi-dabberu/"))
    val gitHubIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/HarshaManideep"))

    SettingItem(modifier = modifier.clickable {
        showBottomSheet= true
    }) {
        Row(
            modifier = Modifier
                .semantics(mergeDescendants = true) {}
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(end = 5.dp),
                imageVector = Icons.Rounded.Support,
                contentDescription = stringResource(R.string.support)
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp),
                fontSize = 18.sp,
                text = stringResource(R.string.support),
            )
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxSize(),
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            Column {
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Icon(
                        modifier= Modifier.padding(5.dp),
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null)
                    Text(
                        modifier= Modifier.padding(5.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        text = stringResource(id = R.string.support)
                    )

                }
               Contribute {
                   context.startActivity(supportMeIntent)
               }

                HorizontalDivider()

                SupportMe {
                    context.startActivity(gitHubIntent)
                }

                HorizontalDivider()


            }
        }
    }
}

@Composable
private fun Contribute(
    modifier: Modifier= Modifier,
    handleClick : () -> Unit
){
    SettingItem(modifier= modifier.clickable {
        handleClick()
    }) {
        Row(
            modifier = Modifier
                .semantics(mergeDescendants = true) {}
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                modifier = Modifier.padding(end = 5.dp),
                imageVector = Icons.Rounded.Link,
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp),
                fontSize = 18.sp,
                text = "Contribute to the project")
        }
    }
}

@Composable
private fun SupportMe(
    modifier: Modifier= Modifier,
    handleClick : () -> Unit
){
    SettingItem(modifier= modifier.clickable {
        handleClick()
    }) {
        Row(
            modifier = Modifier
                .semantics(mergeDescendants = true) {}
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                modifier = Modifier.padding(end = 5.dp),
                imageVector = Icons.Rounded.Recommend,
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp),
                fontSize = 18.sp,
                text = "Support me")
        }
    }
}