package com.example.myassistantappcompose.features.holidays.data.remote.response


import com.google.gson.annotations.SerializedName

data class Timezone(
    @SerializedName("offset")
    val offset: String,
    @SerializedName("zoneabb")
    val zoneabb: String,
    @SerializedName("zonedst")
    val zonedst: Int,
    @SerializedName("zoneoffset")
    val zoneoffset: Int,
    @SerializedName("zonetotaloffset")
    val zonetotaloffset: Int
)