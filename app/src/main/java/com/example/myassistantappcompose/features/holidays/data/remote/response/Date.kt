package com.example.myassistantappcompose.features.holidays.data.remote.response


import com.google.gson.annotations.SerializedName

data class Date(
    @SerializedName("iso")
    val isoDate: String,
)