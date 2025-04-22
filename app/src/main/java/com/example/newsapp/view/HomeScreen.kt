package com.example.newsapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsapp.model.Article
import com.example.newsapp.ui.theme.button
import com.example.newsapp.viewmodel.NewsViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    onArticleClick: (Article) -> Unit
) {

    val categories = listOf("General", "Technology", "Sports", "Business", "Entertainment", "Health", "Science")
    val selectedCategory by viewModel.selectedCategory
    val articles by viewModel.articles
    var currentBannerIndex by remember { mutableStateOf(0) }

    LaunchedEffect(selectedCategory) {
        viewModel.loadHeadlines(selectedCategory)
    }

    LaunchedEffect(articles) {
        while (true) {
            delay(3000)
            if (articles.isNotEmpty()) {
                val itemCount = articles.take(4).size
                if (itemCount > 0) {
                    currentBannerIndex = (currentBannerIndex + 1) % itemCount
                }
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()) {
        TopAppBar(
            title = {
                Text(
                    "NEWS",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                ) },

            actions = {
                var expanded by remember { mutableStateOf(false) }
                Box {
                    Column (
                        modifier = Modifier
                            .size(116.dp,40.dp)
                            .padding(end = 16.dp)
                            .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
                            .background(button)
                            .clickable { expanded=true },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){

                        Text(selectedCategory, color = Color.White)
                    }

                    DropdownMenu(
                        containerColor = Color.White,
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category) },
                                onClick = {
                                    viewModel.setSelectedCategory(category)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 16.dp)
        ) {
            if (articles.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Wait for news", fontSize = 16.sp)
                }
            } else {
                Column {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
                            .fillMaxWidth()
                            .height(180.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val bannerArticles = articles.take(4)
                        if (bannerArticles.isNotEmpty()) {
                            val article = bannerArticles[currentBannerIndex]
                            AsyncImage(
                                model = article.urlToImage,
                                contentDescription = "Top News Image",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { onArticleClick(article) },
                                contentScale = ContentScale.Crop,
                                error = null
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 165.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(articles.take(4).size) { index ->
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(
                                            color = if (index == currentBannerIndex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                            shape = CircleShape
                                        )
                                        .padding(2.dp)
                                )
                            }
                        }
                    }

                }
            }
        }
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(articles) { article ->
                NewsItem(article = article, onClick = { onArticleClick(article) })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}