package com.example.rickandmorty.entity


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<Results>
)