package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.prefs.AppDatastore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(private val dataStore: AppDatastore): ViewModel() {

      suspend fun readRunInfo(): Boolean{
        return dataStore.readRunInfo()
    }

    fun saveRunInfo(value: Boolean) = viewModelScope.launch {
        dataStore.saveRunInfo(value)
    }

}