package com.example.myassistantappcompose.features.countries.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.core.presentation.util.Resource
import com.example.myassistantappcompose.features.countries.data.repository.HolidayRepository
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
        holidayRepository.getCountries(false).collect{
            when(it) {
                is Resource.Success -> {
                    val data  = it.data
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
                        message = it.message ?: "Unknown error"
                    ))
                    state = state.copy(isLoading = false)
                    Log.d("error",it.message ?: "error occured")
                }
                is Resource.Loading -> {
                    state = state.copy(isLoading = it.isLoading)
                }
            }
        }
    }
}