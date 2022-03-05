package com.sample.billeasy.api

import com.sample.billeasy.database.Movies.All_Movie_Data
import com.sample.billeasy.database.Movies.Movie
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface server_apis {
    @GET("/movie/top_rated")
    suspend fun getRegistered(
        @Query("api_key") api_key : String,
        @Query("language") language : String,
        @Query("page") page : String,
    ) : Response<All_Movie_Data>
}