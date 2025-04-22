package com.example.newsapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapp.model.Article

@Composable
fun NewsItem(
    article: Article,
    onClick: () -> Unit
){

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick() },
        colors=CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ){
        Row (
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column (
                modifier = Modifier
                    .background(Color.White)
                    .weight(3f)
            ){
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )
                article.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 3
                    )
                }
            }

            AsyncImage(
                model = article.urlToImage,
                contentDescription = "News Image",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .weight(1f)
                    .size(90.dp),
                contentScale = ContentScale.Crop,
                error = null
            )

        }
    }
}