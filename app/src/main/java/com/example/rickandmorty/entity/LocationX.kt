package com.example.rickandmorty.entity


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationX(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
): Parcelable