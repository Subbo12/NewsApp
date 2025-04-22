package com.example.newsapp.model

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country:String = "us",
        @Query("category") category:String?=null,
        @Query("apiKey") apiKey:String
    ):NewsResponse

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q") query:String,
        @Query("apiKey") apiKey: String
    ):NewsResponse
}