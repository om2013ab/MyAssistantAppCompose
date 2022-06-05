package com.example.myassistantappcompose.features.holidays.presentation.countries

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.features.holidays.data.remote.models.CountriesInfo
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CountriesScreen(
    navigator: DestinationsNavigator,
    viewModel: CountriesViewModel = hiltViewModel(),
    hideBottomNav: Boolean = true
) {
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state

    LaunchedEffect(key1 = scaffoldState) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.message
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            StandardTopBar(
                title = "Choose a country",
                navigationIcon = Icons.Default.ArrowBack,
                onNavigationIconClick = { navigator.popBackStack() }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            LazyColumn {
                val countries = state.response?.response?.countries ?: emptyList()
                items(items = countries) { country ->
                    CountryItem(country)
                    Divider()
                }
            }
        }

    }
}

@Composable
private fun CountryItem(country: CountriesInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = country.countryName)
        Text(text = "${country.totalHolidays} holidays", fontSize = 13.sp)
    }
}
