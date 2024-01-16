package com.ibrahimcanerdogan.nves.data.model

import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    val sourceID: String?,
    @SerializedName("name")
    val sourceName: String?
)