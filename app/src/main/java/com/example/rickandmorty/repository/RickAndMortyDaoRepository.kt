package com.example.rickandmorty.repository

import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.entity.Characters
import com.example.rickandmorty.entity.Location
import com.example.rickandmorty.retrofit.RickAndMortyDaoInterface
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RickAndMortyDaoRepository @Inject constructor(private val rmDao: RickAndMortyDaoInterface) {

    var charactersList = MutableLiveData<List<Characters>>()

    init {
        charactersList = MutableLiveData()
    }

    fun getCharactersInLocation(): MutableLiveData<List<Characters>> {
        return charactersList
    }



    suspend   fun getLocations(page: Int): Response<Location> {
        delay(500L)
        return rmDao.location(page)
    }

    fun charactersInLocation(characterId: String) {
        try {
            rmDao.getCharactersInLocation(characterId)
                .enqueue(object : Callback<List<Characters>> {
                    override fun onResponse(
                        call: Call<List<Characters>>?,
                        response: Response<List<Characters>>?,
                    ) {

                        charactersList.value = response!!.body()
                    }

                    override fun onFailure(call: Call<List<Characters>>?, t: Throwable?) {
                        TODO("Not yet implemented")
                    }
                })
        } catch (e: Exception) {
        }
    }

}


