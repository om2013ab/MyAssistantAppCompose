package com.example.myassistantappcompose.core.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
){
    private val Context.dataStore by preferencesDataStore("country_store")

    private val countryNameKey = stringPreferencesKey("country_name")
    private val countryIsoKey = stringPreferencesKey("country_iso")

    suspend fun saveCountryName(name: String) {
        context.dataStore.edit {
            it[countryNameKey] = name
        }
    }

    suspend fun saveCountryIso(iso: String) {
        context.dataStore.edit {
            it[countryIsoKey] = iso
        }
    }

    val getCountryName: Flow<String?> = context.dataStore.data
        .map { it[countryNameKey] ?: "" }

    val getCountryIso: Flow<String?> = context.dataStore.data
        .map { it[countryIsoKey] ?: "" }
}