package com.zul.moviedbapi.data.network

import com.zul.moviedbapi.data.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/{id}")
    fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Call<Movie>
}
