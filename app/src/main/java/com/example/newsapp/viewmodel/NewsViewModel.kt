package com.example.newsapp.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.FAVORITE_CATEGORIES_KEY
import com.example.newsapp.dataStore
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject



@HiltViewModel
class NewsViewModel @Inject constructor(
    val newsApiService: NewsApiService,
    val apiKey:String,
    val context: Context,
    val savedStateHandle: SavedStateHandle

) :ViewModel() {

    private val _articles= mutableStateOf<List<Article>>(emptyList())
    val articles : State<List<Article>> = _articles

    private val _searchArticles = mutableStateOf<List<Article>>(emptyList())
    val searchArticle:State<List<Article>> = _searchArticles

    private val _favoriteArticles = mutableStateOf<List<Article>>(emptyList())
    val favoriteArticles:State<List<Article>> = _favoriteArticles

    val favoriteCategoriesFlow : Flow<List<String>> = context.dataStore.data.map { preferences ->
        preferences[FAVORITE_CATEGORIES_KEY]?.let { Json.decodeFromString(it) } ?: emptyList()
    }

    private val _selectedCategory = mutableStateOf(savedStateHandle.get<String>("selectedCategory") ?: "General")
    val selectedCategory: State<String> = _selectedCategory

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
        savedStateHandle.set("selectedCategory", category)
        loadHeadlines(category)
    }


    fun loadHeadlines(category:String){
        viewModelScope.launch {
            _articles.value=try {
                newsApiService.getTopHeadlines(
                    category = category.lowercase(),
                    apiKey = apiKey
                ).articles
            }catch (e:Exception) {
                println("Error loading articles : ${e.message}")
                emptyList()
            }
        }
    }

    fun searchNews(query:String){
        viewModelScope.launch {
            _searchArticles.value=if (query.isNotEmpty()){
                try {
                    newsApiService.searchNews(query,apiKey).articles
                }catch (e:Exception){
                    println("Error loading search articles : ${e.message}")
                    emptyList()
                }
            }else{
                emptyList()
            }
        }
    }

    fun loadFavoriteArticles(){
        viewModelScope.launch {
            val allArticles = mutableListOf<Article>()
            favoriteCategoriesFlow.collect{categories ->
                categories.forEach { category ->
                    val response = try {
                        newsApiService.getTopHeadlines(
                            category = category.lowercase(),
                            apiKey = apiKey
                        ).articles
                    }catch (e:Exception){
                        println("Error loading favorite articles : ${e.message}")
                        emptyList()
                    }
                    allArticles.addAll(response)
                }
                _favoriteArticles.value=allArticles.distinctBy { it.content }
            }
        }
    }

   fun updateFavoriteCategories(category: String,isSelected:Boolean){
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                val currentList = favoriteCategoriesFlow.first()
                val updatedList = if (isSelected){
                    currentList - category
                }else{
                    currentList + category
                }
                preferences[FAVORITE_CATEGORIES_KEY] = Json.encodeToString(updatedList)
            }
        }
    }
}