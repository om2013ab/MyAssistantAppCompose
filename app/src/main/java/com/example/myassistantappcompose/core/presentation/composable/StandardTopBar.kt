package com.example.myassistantappcompose.core.presentation.composable

import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun StandardTopBar(
    @StringRes title: Int,
    navigationIcon: ImageVector? = null,
    onBackArrowClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = title))
        },
        navigationIcon = if (navigationIcon != null){
            {
                IconButton(onClick = {onBackArrowClick()}) {
                    Icon(imageVector = navigationIcon, contentDescription = null)
                }
            }

        }else null
    )
}