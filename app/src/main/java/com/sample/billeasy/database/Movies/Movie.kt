package com.sample.billeasy.database.Movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Movie")
class Movie {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    var movie_id: Int = 0

    @ColumnInfo(name = "movie_img")
    lateinit var movie_img: String

    @ColumnInfo(name = "movie_name")
    lateinit var movie_name: String

    @ColumnInfo(name = "movie_desc")
    lateinit var movie_desc: String


}