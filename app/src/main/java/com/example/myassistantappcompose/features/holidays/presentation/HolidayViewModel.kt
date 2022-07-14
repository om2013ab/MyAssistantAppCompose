package com.example.myassistantappcompose.features.holidays.presentation

import android.app.Application
import android.content.Context
import android.telephony.TelephonyManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.core.data.DataStoreManager
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.core.presentation.util.Resource
import com.example.myassistantappcompose.features.holidays.data.local.HolidaysEntity
import com.example.myassistantappcompose.features.holidays.data.mapper.toHolidaysEntity
import com.example.myassistantappcompose.features.holidays.data.repository.HolidayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HolidayViewModel @Inject constructor(
    application: Application,
    private val repository: HolidayRepository,
    private val dataStoreManager: DataStoreManager
) : AndroidViewModel(application) {


    var holidayState by mutableStateOf(HolidayState())
        private set

    private val uiEventChannel = Channel<UiEvent>()
    val uiEvent = uiEventChannel.receiveAsFlow()


    init {
        viewModelScope.launch {
            dataStoreManager.getCountryIso.collect{
                Log.d("HolidayViewModel", it ?: "not found")
                holidayState = holidayState.copy(countryIso = it)
            }
        }
        viewModelScope.launch {
            dataStoreManager.getCountryName.collect{
                Log.d("HolidayViewModel", it ?: "Not found")
                holidayState = holidayState.copy(countryName = it)
            }
        }
        getAllHolidays()
    }

    private fun getAllHolidays() = viewModelScope.launch {
        repository.getAllHolidays(true, holidayState.countryIso ?: "my",2022).collect{ response ->
            when(response){
                is Resource.Success ->{
                    val holidays = response.data?.response?.holidays
                    if (holidays != null) {
                        holidayState = holidayState.copy(
                            holidays = holidays.map { it.toHolidaysEntity() },
                        )
                    }
                }
                is Resource.Error -> {
                    uiEventChannel.send(UiEvent.ShowSnackBar(
                        message = response.message ?: "Unknown error"
                    ))
                    holidayState = holidayState.copy(isLoading = false)
                }
                is Resource.Loading -> {
                    holidayState = holidayState.copy(isLoading = response.isLoading)
                }
            }
        }
    }

    fun onHolidayClick(holiday: HolidaysEntity) {
        holidayState = holidayState.copy(showDialog = true, clickedHoliday = holiday)
    }

    fun onDismissDialog() {
        holidayState = holidayState.copy(showDialog = false)
    }

    fun getUserCountryIso(): String {
        val telephonyManager =
            getApplication<Application>().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.networkCountryIso.uppercase()
    }

    fun toggleMonthSelection(selectedMonth: Int) {
        holidayState = holidayState.copy(monthIndex = selectedMonth)
    }

}