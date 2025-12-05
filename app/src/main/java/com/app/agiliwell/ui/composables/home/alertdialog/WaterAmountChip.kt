package com.app.agiliwell.ui.composables.home.alertdialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.app.agiliwell.R

@Composable
fun WaterAmountChip(
    waterAmount:String
) {
    var selected by remember { mutableStateOf(false) }

    FilterChip(
        modifier = Modifier.wrapContentWidth(),
        onClick = { selected = !selected },
        label = {
            Text(
                maxLines = 1,
                text = waterAmount)
        },
        selected = selected,
        leadingIcon = {
                      Image(painter = painterResource(
                          id = R.drawable.ic_juice_glass), contentDescription = null )
        },
        trailingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = stringResource(R.string.done),
                )
            }
        } else {
            null
        },
    )
}