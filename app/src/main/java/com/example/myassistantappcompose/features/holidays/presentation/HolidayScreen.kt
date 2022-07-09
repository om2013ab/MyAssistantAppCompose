package com.example.myassistantappcompose.features.holidays.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.core.presentation.composable.InfoAlertDialog
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.features.destinations.CountriesScreenDestination
import com.example.myassistantappcompose.features.holidays.data.local.HolidaysEntity
import com.example.myassistantappcompose.features.holidays.util.takeDay
import com.example.myassistantappcompose.features.holidays.util.takeMonth
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun HolidayScreen(
    navigator: DestinationsNavigator,
    viewModel: HolidayViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    var selectedMonth by remember {
        mutableStateOf(0)
    }
    val state by viewModel.state.collectAsState()
    var clickedHoliday by remember {
        mutableStateOf(state.holidays?.get(0))
    }
    LaunchedEffect(key1 = scaffoldState) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Navigate -> {
                    navigator.navigate(it.destination)
                }
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
                title = state.countryName ?: "Malaysia",
                actionIcon = Icons.Default.ExpandMore,
                onActionIconClick = { navigator.navigate(CountriesScreenDestination()) }
            )
        }
    ) {
        if (state.showDialog) {
            InfoAlertDialog(
                title = R.string.holiday_description,
                text = clickedHoliday?.description ?: "",
                onDismiss = {viewModel.onDismissDialog()}
            )
        }
        LazyColumn {
            item {
                ChipsSection(
                    months = context.resources.getStringArray(R.array.months).toList(),
                    selectedMonth = {
                        selectedMonth = it
                    }
                )
            }
            val holidays = state.holidays?.filter {
                it.isoDate.takeMonth() == selectedMonth + 1
            }
            items(items = holidays ?: emptyList()) { holiday ->
                HolidayItem(
                    holiday = holiday,
                    onItemClick = {
                        clickedHoliday = holiday
                        viewModel.onShowDialog()
                    }
                )

            }

        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun ChipsSection(
    months: List<String>,
    selectedMonth: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .background(Color.LightGray)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        var selectedState by remember {
            mutableStateOf(0)
        }
        months.forEachIndexed { index, month ->
            FilterChip(
                colors = ChipDefaults.filterChipColors(
                    selectedBackgroundColor = MaterialTheme.colors.primary,
                    selectedContentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = {
                    selectedState = index
                    selectedMonth(selectedState)
                },
                selected = selectedState == index
            ) {
                Text(text = month)
            }
        }
    }
}

@Composable
fun HolidayItem(
    holiday: HolidaysEntity,
    onItemClick: () -> Unit
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onItemClick() }
                .padding(10.dp)
        ) {
            Text(
                text = holiday.isoDate.takeDay(),
                color = MaterialTheme.colors.primary,
                fontSize = 37.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.width(18.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = holiday.name)
                Text(text = holiday.locations)
            }
        }
        Divider()
    }


}

@Preview()
@Composable
fun HolidayItemPreview() {
    HolidayItem(
        holiday = HolidaysEntity(
            name = "Example Holiday",
            description = "",
            locations = "All state",
            isoDate = "2020-03-16",
        ),
        onItemClick = {}
    )
}


