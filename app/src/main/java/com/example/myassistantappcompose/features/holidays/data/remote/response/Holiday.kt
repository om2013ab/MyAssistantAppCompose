package com.example.myassistantappcompose.features.holidays.data.remote.response


import com.google.gson.annotations.SerializedName

data class Holiday(
    @SerializedName("date")
    val date: Date,
    @SerializedName("description")
    val description: String,
    @SerializedName("locations")
    val locations: String,
    @SerializedName("name")
    val name: String
)