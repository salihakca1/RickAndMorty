package com.example.rickandmorty.retrofit

class ApiUtils {

    companion object{
        fun getRickAndMortyDaoInterface(): RickAndMortyDaoInterface{
            return  RetrofitClient.getClient(ConstantsRetrofit.BASE_URL).create(RickAndMortyDaoInterface::class.java)
        }
    }
}