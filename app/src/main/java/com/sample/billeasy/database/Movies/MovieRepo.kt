package com.sample.billeasy.database.Movies

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sample.billeasy.api.server_apis
import com.sample.billeasy.utils.RetrofitObjectInstance
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class MovieRepo constructor(private val movieDao: MovieDao) {
    val allMovies: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
    val statusOnDeletion: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun fetchUpdatedChats(){
        movieDao.fetchAllMovies().
        observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnNext {
                allMovies.value = it
            }.doOnError { it -> Log.e("MovieDBRepo1", "${it.message}") }
            .subscribe()
    }
    fun returnFetchUpdatedRecords() : LiveData<List<Movie>> {
        return allMovies
    }

    fun insertAllMovies(movies : List<Movie>){
        movieDao
            .insertMovies(movies)
            .subscribeOn(Schedulers.io())
            .doOnError {
                Log.e("error in MovieRepo 31","database is not created")
            }
            .subscribe {
            Log.e("Number of movies added","${it.size}")
        }
    }


    fun fetchingMovies(){

        movieDao
            .deleteAllMovies()
            .subscribeOn(Schedulers.io())
            .doOnComplete {

                // fetching new movie from server and adding them to room db

                CoroutineScope(Dispatchers.IO).launch {
                    var s_a : server_apis = RetrofitObjectInstance.getInstance().create(server_apis::class.java)
                    var results : Response<All_Movie_Data> = s_a.getRegistered(
                        "508eca39454ac228bd182e5463a76905",
                        "en-US",
                        "1"
                    )
                    if(results.isSuccessful){
                        Log.e("url", results.message())
                        if(results.body()?.results!!.size>0){
                            insertAllMovies(results.body()?.results!!)
                        }
                    }
                    else{
                        Log.e("failure result","${results.message()}")
                    }

                }
            }
            .subscribe()

    }
}