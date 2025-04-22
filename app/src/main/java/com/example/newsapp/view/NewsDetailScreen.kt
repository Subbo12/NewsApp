package com.example.newsapp.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapp.model.Article
import com.example.newsapp.ui.theme.back
import com.example.newsapp.ui.theme.button

@Composable
fun NewsDetailScreen(article: Article,onBackClick: () -> Unit){

    Column (
        modifier = Modifier
            .background(back)
            .fillMaxSize()
            .padding(16.dp)
    ){

        Column (
            modifier = Modifier
                .size(80.dp,40.dp)
                .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
                .background(button)
                .clickable { onBackClick() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
           Row (
               modifier = Modifier.padding(end = 10.dp),
               horizontalArrangement = Arrangement.SpaceBetween,
               verticalAlignment = Alignment.CenterVertically
           ){
               Icon(Icons.Default.KeyboardArrowLeft,"kd", tint = Color.White)
              Text("Back", color = Color.White)
           }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = article.urlToImage,
            contentDescription = "News Image",
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop,
            error = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .padding(10.dp),
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                article.description?.let {
                    Text(
                        modifier = Modifier
                            .padding(10.dp),
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                article.content?.let {
                    Text(
                        modifier = Modifier
                            .padding(10.dp),
                        text = it,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                val context = LocalContext.current

                TextButton(
                    onClick = {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            println("Error opening URL: ${e.message}")
                        }
                    }
                ) {
                    Text(text = "See More", color = button)
                }
            }
        }
    }
}