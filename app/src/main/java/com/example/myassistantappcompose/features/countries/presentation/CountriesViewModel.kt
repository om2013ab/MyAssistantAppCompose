package com.example.myassistantappcompose.features.countries.presentation

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.core.presentation.util.Resource
import com.example.myassistantappcompose.features.countries.data.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val repository: CountryRepository,
    application: Application
): AndroidViewModel(application) {

    var state by mutableStateOf(CountriesState())
        private set


    private val uiEventChannel = Channel<UiEvent>()
    val uiEvent = uiEventChannel.receiveAsFlow()

    init {
        getAllCountries()
    }

    private fun getAllCountries() = viewModelScope.launch {
        repository.getCountries(false).collect{
            when(it) {
                is Resource.Success -> {
                    val data  = it.data
                    if (data != null) {
                        state = state.copy(
                            response = data,
                            isLoading = false
                        )
                    }
                }
                is Resource.Error -> {
                    uiEventChannel.send(UiEvent.ShowSnackBar(
                        message = it.message ?: "Unknown error"
                    ))
                    state = state.copy(isLoading = false)
                }
                is Resource.Loading -> {
                    state = state.copy(isLoading = it.isLoading)
                }
            }
        }
    }


    fun onUpdateSelectedCountry(name: String, iso: String) = viewModelScope.launch {
        if (isNetworkAvailable(getApplication())) {
            repository.updateSelectedCountry(name, iso)
        } else {
            uiEventChannel.send(UiEvent.ShowToast("No Internet Connection"))
        }
        uiEventChannel.send(UiEvent.PopBackStack)
    }


    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}