package com.example.myassistantappcompose.features.holidays.data.remote.response


import com.google.gson.annotations.SerializedName

data class HolidayResponse(
    @SerializedName("response")
    val response: Response
)