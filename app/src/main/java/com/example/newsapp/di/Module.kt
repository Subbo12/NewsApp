package com.example.newsapp.di

import android.content.Context
import com.example.newsapp.model.NewsApiService
import com.example.newsapp.model.RetrofitInstance.API_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {


    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit):NewsApiService{
        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiKey():String{
        return API_KEY
    }


    @Provides
    @Singleton
    fun provideContext(application: android.app.Application): Context {
        return application.applicationContext
    }
}