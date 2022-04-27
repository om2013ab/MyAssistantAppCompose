package com.example.myassistantappcompose.core.presentation.composable

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myassistantappcompose.core.util.BottomNavDestinations
import com.example.myassistantappcompose.features.destinations.Destination
import com.ramcosta.composedestinations.spec.Direction

@Composable
fun BottomBarSection(
    destinations: List<BottomNavDestinations>,
    currentDestination: Destination?,
    onBottomBarItemClick: (Direction) -> Unit
) {
    BottomAppBar(
        backgroundColor = Color.White
    ) {
        destinations.forEach { destination ->
            val isSelected = currentDestination == destination.direction
            BottomNavigationItem(
                selected = isSelected,
                icon = {
                    Icon(
                        imageVector = if (isSelected) destination.selectedIcon else destination.unSelectedIcon,
                        contentDescription = stringResource(id = destination.label),
                        tint = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.primary.copy(alpha = 0.8f)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = destination.label),
                        color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.primary.copy(alpha = 0.8f),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                onClick = {
                    onBottomBarItemClick(destination.direction)
                }
            )
        }
    }
}