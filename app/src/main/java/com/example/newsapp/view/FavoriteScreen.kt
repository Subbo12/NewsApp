package com.example.newsapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.newsapp.model.Article
import com.example.newsapp.ui.theme.button
import com.example.newsapp.viewmodel.NewsViewModel

@Composable
fun FavoriteScreen(
    viewModel: NewsViewModel,
    onArticleClick : (Article) -> Unit
){

    val favoriteCategories by viewModel.favoriteCategoriesFlow.collectAsState(initial = emptyList())
    val articles by viewModel.favoriteArticles
    val categories = listOf("General", "Technology", "Sports", "Business", "Entertainment", "Health", "Science")

    LaunchedEffect (favoriteCategories){
        viewModel.loadFavoriteArticles()
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){

        var expanded by remember { mutableStateOf(false) }

        Column (
            modifier = Modifier
                .size(100.dp,40.dp)
                .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
                .clickable { expanded=true }
                .background(button),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text("Favorite", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column {
            DropdownMenu(

                containerColor = Color.White,
                expanded = expanded,
                onDismissRequest = {expanded=false}
            ) {
                categories.forEach { category ->
                    val isSelected = favoriteCategories.contains(category)
                    DropdownMenuItem(

                        text = {
                            Row (verticalAlignment = Alignment.CenterVertically){
                                Checkbox(
                                    colors = CheckboxColors(
                                        checkedCheckmarkColor = Color.White,
                                        uncheckedCheckmarkColor = Color.White,
                                        checkedBoxColor = button,
                                        uncheckedBoxColor = Color.White,
                                        disabledCheckedBoxColor = Color.White,
                                        disabledUncheckedBoxColor = Color.White,
                                        disabledIndeterminateBoxColor = Color.Red,
                                        checkedBorderColor = button,
                                        uncheckedBorderColor = Color.Black,
                                        disabledBorderColor = Color.Black,
                                        disabledUncheckedBorderColor = Color.Black,
                                        disabledIndeterminateBorderColor = Color.Black
                                    ),
                                    checked = isSelected,
                                    onCheckedChange = null
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(category)
                            }
                        },
                        onClick = {
                            viewModel.updateFavoriteCategories(category , isSelected)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(articles){article ->
                NewsItem(article = article, onClick = {onArticleClick(article)})
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }

}