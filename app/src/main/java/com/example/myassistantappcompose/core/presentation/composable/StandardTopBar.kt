package com.example.myassistantappcompose.core.presentation.composable

import androidx.annotation.StringRes
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun StandardTopBar(
    @StringRes title: Int,

) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = title))
        },
    )
}