package com.example.android.marsrealestate.network

import com.squareup.moshi.Json

sealed class MarsPropertyResponse {
    object Empty : MarsPropertyResponse()

    object Loading : MarsPropertyResponse()

    data class Success(
        val listings: List<MarsPropertyListing>
    ) : MarsPropertyResponse()

    data class Error(val errorCode: Int, val errorMessage: String) : MarsPropertyResponse()
}

data class MarsPropertyListing(
    val id: String,
    @Json(name = "img_src")
    val imgSrcUrl: String,
    val type: String,
    val price: Double
)


