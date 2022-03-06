package com.sample.billeasy.ui

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.sample.billeasy.api.server_apis
import com.sample.billeasy.database.MovieDb
import com.sample.billeasy.database.Movies.All_Movie_Data
import com.sample.billeasy.database.Movies.Movie
import com.sample.billeasy.database.Movies.MovieDao
import com.sample.billeasy.database.Movies.MovieRepo
import com.sample.billeasy.utils.RetrofitObjectInstance
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class BHPViewmodel(): ViewModel() {

    public var all_movie_list : MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
    private lateinit var movie_repo: MovieRepo

    fun setUp(movieDao: MovieDao){
        movie_repo = MovieRepo(movieDao)
    }

    private val movieListObserver = Observer<List<Movie>> {
        Log.e("total number of data","${it.size}")
        all_movie_list.value = it
    }

    public fun get_all_movie_list() : LiveData<List<Movie>>{
        return all_movie_list
    }

    public fun fetch_all_movie_list_from_roomdb(){

        movie_repo.returnFetchUpdatedRecords()
            .observeForever(movieListObserver)
        movie_repo.fetchUpdatedChats()
    }

    fun fetch_all_movie_from_server(){
        movie_repo.fetchingMovies()

    }

    override fun onCleared() {
        movie_repo.returnFetchUpdatedRecords().removeObserver(movieListObserver)
        super.onCleared()
    }

}