package com.example.myassistantappcompose.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myassistantappcompose.core.presentation.composable.BottomBarSection
import com.example.myassistantappcompose.core.presentation.ui.theme.MyAssistantAppComposeTheme
import com.example.myassistantappcompose.core.presentation.ui.theme.PrimaryColor
import com.example.myassistantappcompose.core.util.BottomNavDestinations
import com.example.myassistantappcompose.features.NavGraphs
import com.example.myassistantappcompose.features.navDestination
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigateTo
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAssistantAppComposeTheme {
                val systemUiController = rememberSystemUiController()
                val statusBarColor = MaterialTheme.colors.primary
                SideEffect {
                    systemUiController.setStatusBarColor(color = statusBarColor)
                }
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val hideBottomNav = navBackStackEntry?.arguments?.getBoolean(ARG_BOTTOM_NAV_VISIBILITY)
                Scaffold(
                    backgroundColor = MaterialTheme.colors.background,
                    bottomBar = {
                        if (hideBottomNav == null || !hideBottomNav) {
                            BottomBarSection(
                                destinations = listOf(
                                    BottomNavDestinations.CoursesScreen,
                                    BottomNavDestinations.TimetableScreen,
                                    BottomNavDestinations.AssignmentsScreen,
                                    BottomNavDestinations.TestsScreen,
                                    BottomNavDestinations.HolidaysScreen
                                ),
                                currentDestination = navBackStackEntry?.navDestination,
                                onBottomBarItemClick = {
                                    navController.popBackStack()
                                    navController.navigateTo(it){
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root, navController = navController, modifier = Modifier.padding(it))
                }
            }
        }
    }
}

private const val ARG_BOTTOM_NAV_VISIBILITY = "hideBottomNav"
