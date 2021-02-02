package com.example.android.marsrealestate.network

import android.os.Parcel
import android.os.Parcelable
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
): Parcelable {
    val isRental
        get() = type == "rent"

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(imgSrcUrl)
        parcel.writeString(type)
        parcel.writeDouble(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MarsPropertyListing> {
        override fun createFromParcel(parcel: Parcel): MarsPropertyListing {
            return MarsPropertyListing(parcel)
        }

        override fun newArray(size: Int): Array<MarsPropertyListing?> {
            return arrayOfNulls(size)
        }
    }
}


