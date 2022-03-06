package com.sample.billeasy.database.Movies

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe


@Dao
interface MovieDao {

    @Insert()
    fun insertMovies(users: List<Movie>): Maybe<List<Long>>

    @Query("SELECT * FROM Movie")
    fun fetchAllMovies(): Flowable<List<Movie>>

    @Query("DELETE FROM Movie")
    fun deleteAllMovies() : Completable
}