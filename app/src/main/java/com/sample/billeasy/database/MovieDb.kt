package com.sample.billeasy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sample.billeasy.database.Movies.Movie
import com.sample.billeasy.database.Movies.MovieDao


@Database(entities = [Movie::class], version = 1)
abstract class MovieDb : RoomDatabase() {
    abstract fun getMoviesDao(): MovieDao

    companion object {
        private var instance: MovieDb? = null
        fun getDatabase(context: Context?): MovieDb? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context as Context,
                    MovieDb::class.java, "MoviesDatabase"
                ).build()
            }
            return instance
        }
    }
}