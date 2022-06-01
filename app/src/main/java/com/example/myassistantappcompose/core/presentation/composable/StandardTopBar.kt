package com.example.myassistantappcompose.core.presentation.composable

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.myassistantappcompose.R

@Composable
fun StandardTopBar(
    title: String,
    backgroundColor: Color = MaterialTheme.colors.primary,
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
    navigationIcon: ImageVector? = null,
    onNavigationIconClick: () -> Unit = {},
    actionIcon: ImageVector? = null,
    onActionIconClick: () -> Unit = {},
    dropdownMenu: @Composable () -> Unit = {}
) {
    TopAppBar(
        backgroundColor = backgroundColor,
        elevation = elevation,
        title = {
            Text(text = title)
        },
        navigationIcon = if (navigationIcon != null){
            {
                IconButton(onClick = {onNavigationIconClick()}) {
                    Icon(imageVector = navigationIcon, contentDescription = null)
                }
            }

        } else null,
        actions = {
                if (actionIcon != null) {
                    IconButton(onClick = { onActionIconClick() }) {
                        Icon(
                            imageVector = actionIcon,
                            contentDescription = stringResource(R.string.more_option)
                        )
                        dropdownMenu()
                    }
                }
        }
    )
}