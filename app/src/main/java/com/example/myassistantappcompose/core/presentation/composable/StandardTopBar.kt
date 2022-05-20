package com.example.myassistantappcompose.core.presentation.composable

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.myassistantappcompose.R

@Composable
fun StandardTopBar(
    @StringRes title: Int,
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
    navigationIcon: ImageVector? = null,
    onBackArrowClick: () -> Unit = {},
    showMenuActionIcon: Boolean = false,
    onMenuIconClick: () -> Unit = {},
    dropdownMenu: @Composable () -> Unit = {}
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

        } else null,
        actions = {
            if (showMenuActionIcon) {
                IconButton(onClick = { onMenuIconClick() }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.more_option)
                    )
                    dropdownMenu()
                }
            }
        }
    )
}