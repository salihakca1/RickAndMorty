package com.example.rickandmorty.repository

import android.annotation.SuppressLint
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.entity.Results
import javax.inject.Inject


class PagingRepository @Inject constructor (private val rickAndMortyDaoRepository: RickAndMortyDaoRepository): PagingSource<Int, Results>() {

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = rickAndMortyDaoRepository.getLocations(nextPageNumber)
            val locations = response.body()!!.results?: emptyList()
            val nextPage = if (response.body()!!.info.next.isNullOrEmpty()) null else nextPageNumber + 1
                LoadResult.Page(
                data = locations ,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}