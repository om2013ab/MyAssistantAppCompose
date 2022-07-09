package com.example.myassistantappcompose.features.holidays.presentation

import android.app.Application
import android.content.Context
import android.telephony.TelephonyManager
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.core.data.DataStoreManager
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.core.presentation.util.Resource
import com.example.myassistantappcompose.features.destinations.CountriesScreenDestination
import com.example.myassistantappcompose.features.holidays.data.mapper.toHolidaysEntity
import com.example.myassistantappcompose.features.holidays.data.repository.HolidayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HolidayViewModel @Inject constructor(
    application: Application,
    private val repository: HolidayRepository,
    private val dataStoreManager: DataStoreManager
) : AndroidViewModel(application) {



    private val _state = MutableStateFlow(HolidayState())
    val state = _state.asStateFlow()

    private val uiEventChannel = Channel<UiEvent>()
    val uiEvent = uiEventChannel.receiveAsFlow()




    init {
        viewModelScope.launch {
            dataStoreManager.getCountryIso.collect{
                Log.d("HolidayViewModel", it ?: "not found")
                _state.value = _state.value.copy(countryIso = it)
            }
        }
        viewModelScope.launch {
            dataStoreManager.getCountryName.collect{
                Log.d("HolidayViewModel", it ?: "Not found")
                _state.value = _state.value.copy(countryName = it)
            }
        }
        getAllHolidays()
    }

    private fun getAllHolidays() = viewModelScope.launch {
        repository.getAllHolidays(true, state.value.countryIso ?: "my",2022).collect{
            when(it){
                is Resource.Success ->{
                    val holidays = it.data?.response?.holidays
                    if (holidays != null) {
                        _state.value = _state.value.copy(
                            holidays = holidays.map { it.toHolidaysEntity() },
                        )
                    }
                }
                is Resource.Error -> {
                    uiEventChannel.send(UiEvent.ShowSnackBar(
                        message = it.message ?: "Unknown error"
                    ))
                    _state.value = _state.value.copy(isLoading = false)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = it.isLoading)
                }
            }
        }
    }

    fun onShowDialog() {
        _state.value = _state.value.copy(showDialog = true)
    }

    fun onDismissDialog() {
        _state.value = _state.value.copy(showDialog = false)
    }

    fun getUserCountryIso(): String {
        val telephonyManager =
            getApplication<Application>().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.networkCountryIso.uppercase()
    }

}