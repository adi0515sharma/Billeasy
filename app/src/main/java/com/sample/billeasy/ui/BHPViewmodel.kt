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
        all_movie_list.value = it
    }

    public fun get_all_movie_list() : LiveData<List<Movie>>{
        return all_movie_list
    }

    public fun fetch_all_movie_list_from_roomdb(){
        movie_repo.returnFetchUpdatedRecords().observeForever(movieListObserver)
        movie_repo.fetchUpdatedChats()
    }

    override fun onCleared() {
        movie_repo.returnFetchUpdatedRecords().removeObserver(movieListObserver)
        super.onCleared()
    }

    fun fetch_all_movie_from_server(){

        viewModelScope.launch {
            var s_a : server_apis = RetrofitObjectInstance.getInstance().create(server_apis::class.java)
            var results : Response<All_Movie_Data> = s_a.getRegistered(
                "508eca39454ac228bd182e5463a76905",
                "en-US",
                "1"
            )
            if(results.isSuccessful){
                Log.e("url", results.message())
                if(results.body()?.results!!.size>0){
                    movie_repo.insertAllMovies(results.body()?.results!!)
                }
            }
        }

    }


}