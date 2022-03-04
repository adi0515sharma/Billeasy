package com.sample.billeasy.database.Movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MovieRepo constructor(private val movieDao: MovieDao) {
    val allMovies: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()

    fun fetchUpdatedChats(){
        movieDao.fetchAllMovies().observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                allMovies.value = it
            }.doOnError { it -> Log.e("MovieDBRepo1", "${it.message}") }
            .subscribe()
    }
    fun returnFetchUpdatedRecords() : LiveData<List<Movie>> {
        return allMovies
    }

    fun insertAllMovies(movies : List<Movie>){
        movieDao.insertMovies(movies).subscribeOn(Schedulers.io()).subscribe {
            Log.e("Number of movies added","${it.size}")
        }
    }
}