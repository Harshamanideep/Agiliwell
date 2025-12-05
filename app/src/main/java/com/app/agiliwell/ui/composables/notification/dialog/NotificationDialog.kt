package com.app.agiliwell.ui.composables.notification.dialog

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.app.agiliwell.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationDialog(
    modifier: Modifier = Modifier,
    permissionGranted:(state: Boolean) -> Unit
) {
    val openAlertDialog = remember { mutableStateOf(true) }
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )
    if (permissionState.status.isGranted){
        permissionGranted(true)
    } else if (!permissionState.status.isGranted && permissionState.status.shouldShowRationale){
        when {
            openAlertDialog.value -> {
                AlertDialogNotification(
                    onDismissRequest = { openAlertDialog.value = false },
                    onConfirmation = {
                        openAlertDialog.value = false
                        permissionState.launchPermissionRequest()
                    },
                    dialogTitle = stringResource(R.string.reminder_notification),
                    dialogText = stringResource(R.string.notification_permission_message),
                    icon = Icons.Default.Info
                )
            }
        }
    } else{
        LaunchedEffect(key1 = Unit, block = { permissionState.launchPermissionRequest() })
        openAlertDialog.value=true
    }
}

@Composable
fun AlertDialogNotification(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = null)
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(id = R.string.dismiss))
            }
        }
    )
}