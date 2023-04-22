package com.example.rickandmorty.retrofit

import com.example.rickandmorty.entity.Characters
import com.example.rickandmorty.entity.Location
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyDaoInterface {

     @GET("/api/location")
     suspend fun location(@Query("page") pageNumber: Int): Response<Location>

     @GET("/api/character/{characterId}")
     fun getCharactersInLocation(@Path("characterId") characterId: String): Call<List<Characters>>

 }