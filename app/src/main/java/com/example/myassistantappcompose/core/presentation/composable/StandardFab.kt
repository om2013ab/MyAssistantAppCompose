package com.example.myassistantappcompose.core.presentation.composable

import androidx.annotation.StringRes
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun StandardFab(
    modifier: Modifier = Modifier,
    @StringRes contentDesc: Int,
    imageVector: ImageVector = Icons.Default.Add,
    tintColor: Color = Color.White,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = {onClick()}
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = stringResource(contentDesc),
            tint = tintColor
        )
    }
}