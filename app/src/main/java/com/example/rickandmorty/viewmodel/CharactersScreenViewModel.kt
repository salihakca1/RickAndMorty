package com.example.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.entity.Characters
import com.example.rickandmorty.repository.RickAndMortyDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersScreenViewModel @Inject constructor(private val repository: RickAndMortyDaoRepository): ViewModel() {

    var charactersList = MutableLiveData<List<Characters>>()

    init {

        charactersList = repository.getCharactersInLocation()
    }

      fun charactersInLocation(characterId: String){
            viewModelScope.launch {
                repository.charactersInLocation(characterId = characterId)
            }
    }


}