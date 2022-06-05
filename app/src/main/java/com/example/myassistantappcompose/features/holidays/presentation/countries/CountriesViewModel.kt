package com.example.myassistantappcompose.features.holidays.presentation.countries

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.core.util.Resource
import com.example.myassistantappcompose.features.holidays.domain.HolidayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val holidayRepository: HolidayRepository
): ViewModel() {

    var state by mutableStateOf(CountriesState())

    private val uiEventChannel = Channel<UiEvent>()
    val uiEvent = uiEventChannel.receiveAsFlow()

    init {
        getAllCountries()
    }

    private fun getAllCountries() = viewModelScope.launch {
        state = state.copy(isLoading = true)
         when(val response = holidayRepository.getAllCountries()) {
            is Resource.Success -> {
                val data  = response.data
                if (data != null) {
                    state = state.copy(
                        response = data,
                        isLoading = false
                    )
                    Log.d("success","not null")
                }
            }
            is Resource.Error -> {
                uiEventChannel.send(UiEvent.ShowSnackBar(
                    message = response.message ?: "Unknown error"
                ))
                state = state.copy(isLoading = false)
                Log.d("error",response.message ?: "error occured")
            }
        }
    }
}