package com.example.myassistantappcompose.features.countries.presentation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.features.countries.data.remote.response.CountriesInfo
import com.example.myassistantappcompose.features.destinations.HolidayScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun CountriesScreen(
    navigator: DestinationsNavigator,
    viewModel: CountriesViewModel = hiltViewModel(),
    hideBottomNav: Boolean = true
) {
    val context = LocalContext.current
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
                is UiEvent.ShowToast -> {
                    Toast.makeText(context,it.message, Toast.LENGTH_SHORT).show()
                }

                is UiEvent.PopBackStack -> {
                    navigator.popBackStack()
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
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            LazyColumn {
                val countries = state.response?.response?.countries ?: emptyList()
                items(items = countries) { country ->
                    CountryItem(
                        country = country,
                        onCountryClick = {
                            viewModel.onUpdateSelectedCountry(
                                name = country.countryName,
                                iso = country.isoName
                            )
                        }
                    )
                }
            }
        }

    }
}

@Composable
private fun CountryItem(
    country: CountriesInfo,
    onCountryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCountryClick() }
            .padding(start = 12.dp, end = 12.dp, top = 8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = country.countryName)
        Text(text = "${country.totalHolidays} holidays", fontSize = 13.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
    }
}
