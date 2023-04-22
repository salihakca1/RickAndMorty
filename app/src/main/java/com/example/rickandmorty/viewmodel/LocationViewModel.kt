package com.example.rickandmorty.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.rickandmorty.repository.PagingRepository
import com.example.rickandmorty.repository.RickAndMortyDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val repo: RickAndMortyDaoRepository): ViewModel() {

            val locations= Pager(PagingConfig(pageSize = 20)) {
                PagingRepository(repo)
            }.flow.cachedIn(viewModelScope)


}