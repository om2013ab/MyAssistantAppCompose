package com.example.myassistantappcompose.features.holidays.data.remote.response


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("holidays")
    val holidays: List<Holiday>
)