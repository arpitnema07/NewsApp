package com.example.news.network

import com.example.news.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getTopNews(
        @Query("country")
        countryCode:String = "in",
        @Query("apiKey")
        apiKey:String = API_KEY
    ) : Response<NewsResponse>
}