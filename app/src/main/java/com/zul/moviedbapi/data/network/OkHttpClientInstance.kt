package com.zul.moviedbapi.data.network

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object OkHttpClientInstance {
    private val client = OkHttpClient()

    fun fetchMovies(url: String, callback: (String?, IOException?) -> Unit) {
        val request = Request.Builder()
            .url(url)
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzYzAyZDQ1ZGU1NDFjYWM2YzE1YTQ1MzA4MDBiY2E3ZCIsIm5iZiI6MTcyMjYwMTU3NS44MTQyNDMsInN1YiI6IjY2YWFmYjgxODUxMThhZDM2ODUwN2IwZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.wnohbDSEVHkwT6i4_trnNeNFumgCeT_X9EoCd5uKq-M")
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                callback(null, e)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    response.body?.string()?.let {
                        callback(it, null)
                    } ?: callback(null, IOException("Response body is null"))
                } else {
                    callback(null, IOException("Unsuccessful response"))
                }
            }
        })
    }
}
