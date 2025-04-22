package com.example.newsapp.model

data class Article(
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val url: String,
    val content: String? = null
)

