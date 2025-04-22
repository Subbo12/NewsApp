package com.example.newsapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.model.Article
import com.example.newsapp.ui.theme.back
import com.example.newsapp.ui.theme.button
import com.example.newsapp.viewmodel.NewsViewModel

@Composable
fun SearchScreen(
    viewModel: NewsViewModel= hiltViewModel(),
    onArticleClick : (Article) -> Unit
){
    var searchQuery by remember { mutableStateOf("") }
    val articles by viewModel.searchArticle

    LaunchedEffect (searchQuery){
        viewModel.searchNews(searchQuery)
    }

    Column (
        modifier = Modifier
            .background(back)
            .fillMaxSize()
            .padding(16.dp)
    ){
        TextField(
            placeholder ={ Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(Icons.Default.Search,"Search", tint = button)
                Text("Search News")
            } } ,
            modifier = Modifier
                .background(Color.White)
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .fillMaxWidth(),
            value = searchQuery,
            onValueChange = {searchQuery=it},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(articles){article ->
                NewsItem(article = article, onClick = {onArticleClick(article)})
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}