package com.example.myassistantappcompose.core.presentation.composable

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.myassistantappcompose.R

@Composable
fun StandardAlertDialog(
    @StringRes title: Int,
    @StringRes text: Int,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}

) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(title))},
        text = { Text(stringResource(text))},
        confirmButton = {
            TextButton(onClick = {onConfirm()}) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = {onDismiss()}) {
                Text(stringResource(R.string.dismiss), color = Color.Gray)
            }
        }
    )
}

@Composable
fun InfoAlertDialog(
    @StringRes title: Int,
    text: String,
    onDismiss: () -> Unit

) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(title))},
        text = { Text(text)},
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = {onDismiss()}) {
                Text(stringResource(R.string.close_dialog))
            }
        }
    )
}