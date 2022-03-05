package com.sample.billeasy.database.Movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Movie")
class Movie {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    var movie_id: Int = 0

    @ColumnInfo(name = "original_language")
    lateinit var original_language: String

    @ColumnInfo(name = "original_title")
    lateinit var original_title: String

    @ColumnInfo(name = "overview")
    lateinit var overview: String

    @ColumnInfo(name = "popularity")
    lateinit var popularity: String

    @ColumnInfo(name = "release_date")
    lateinit var release_date: String

    @ColumnInfo(name = "title")
    lateinit var title: String
}