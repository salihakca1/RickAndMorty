package com.example.rickandmorty.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.rickandmorty.prefs.Constants
import com.example.rickandmorty.repository.RickAndMortyDaoRepository
import com.example.rickandmorty.retrofit.ApiUtils
import com.example.rickandmorty.retrofit.RickAndMortyDaoInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

        @Provides
        @Singleton
        fun providePreferencesDataStore(
            @ApplicationContext context: Context
        ): DataStore<Preferences> = PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(Constants.DATA_STORE_LOGIN)
        }

        @Provides
        @Singleton
        fun provideRickAndMortyDaoRepository(rmDao: RickAndMortyDaoInterface): RickAndMortyDaoRepository{
            return RickAndMortyDaoRepository(rmDao)
        }


        @Provides
            @Singleton
            fun provideRickAndMortyDao(): RickAndMortyDaoInterface{
                return ApiUtils.getRickAndMortyDaoInterface()
            }

}