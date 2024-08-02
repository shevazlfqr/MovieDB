package com.zul.moviedbapi

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.zul.moviedbapi.data.model.MovieResponse
import com.zul.moviedbapi.data.network.OkHttpClientInstance
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var overviewTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        titleTextView = findViewById(R.id.titleTextView)
        overviewTextView = findViewById(R.id.overviewTextView)

        fetchMovies()
    }

    private fun fetchMovies() {
        val url = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc"
        OkHttpClientInstance.fetchMovies(url) { response, exception ->
            if (exception != null) {
                Log.e("MainActivity", "Failed to fetch movies", exception)
                return@fetchMovies
            }

            response?.let {
                val movieResponse = Gson().fromJson(it, MovieResponse::class.java)
                val firstMovie = movieResponse.results.firstOrNull()
                firstMovie?.let { movie ->
                    runOnUiThread {
                        titleTextView.text = movie.original_title
                        overviewTextView.text = movie.overview
                        Glide.with(this)
                            .load("https://image.tmdb.org/t/p/w500${movie.backdrop_path}")
                            .into(imageView)
                    }
                }
            }
        }
    }
}
