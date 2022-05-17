package com.example.myassistantappcompose.core.presentation.composable

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp

@Composable
fun StandardTopBar(
    @StringRes title: Int,
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
    navigationIcon: ImageVector? = null,
    onBackArrowClick: () -> Unit = {}
) {
    TopAppBar(
        elevation = elevation,
        title = {
            Text(text = stringResource(id = title))
        },
        navigationIcon = if (navigationIcon != null){
            {
                IconButton(onClick = {onBackArrowClick()}) {
                    Icon(imageVector = navigationIcon, contentDescription = null)
                }
            }

        } else null
    )
}