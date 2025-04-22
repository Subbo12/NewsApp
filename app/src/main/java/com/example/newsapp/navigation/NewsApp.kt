package com.example.newsapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.decode
import com.example.newsapp.encode
import com.example.newsapp.model.Article
import com.example.newsapp.ui.theme.back
import com.example.newsapp.ui.theme.button
import com.example.newsapp.view.FavoriteScreen
import com.example.newsapp.view.HomeScreen
import com.example.newsapp.view.NewsDetailScreen
import com.example.newsapp.view.SearchScreen
import com.example.newsapp.viewmodel.NewsViewModel

@Composable
fun NewsApp(
    viewModel: NewsViewModel= hiltViewModel()
){

    val navController = rememberNavController()
    val items = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Favorites
    )

    Scaffold (
        bottomBar = {
            NavigationBar (
                containerColor = Color.White
            ){
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        colors = NavigationBarItemColors(
                            selectedIconColor = button,
                            selectedTextColor = Color.Black,
                            selectedIndicatorColor = back,
                            unselectedIconColor = Color.Black,
                            unselectedTextColor = Color.Black,
                            disabledIconColor = Color.Black,
                            disabledTextColor = Color.Black
                        ),
                        icon = {
                            Icon(
                                imageVector = when(screen){
                                    Screen.Home -> Icons.Default.Home
                                    Screen.Search -> Icons.Default.Search
                                    Screen.Favorites -> Icons.Default.Favorite
                                },
                                contentDescription = screen.route
                            )
                        },
                        label = { Text(screen.route) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route){
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState=true
                                }
                                launchSingleTop = true
                                restoreState=true
                            }
                        }
                    )
                }
            }
        }
    ){ innerPadding ->

        NavHost(
            navController=navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Home.route){
                HomeScreen(viewModel = viewModel, onArticleClick = {article ->
                    navController.navigate(
                        "news_detail" +
                        "?title=${article.title.encode()}"+
                        "&description=${article.description?.encode() ?: ""}"+
                        "&urlToImage=${article.urlToImage?.encode() ?: ""}" +
                        "&url=${article.url.encode()}" +
                        "&content=${article.content?.encode() ?:""}"
                    )
                })
            }

            composable(Screen.Search.route) {
                SearchScreen(viewModel = viewModel, onArticleClick = { article ->
                    navController.navigate(
                        "news_detail" +
                                "?title=${article.title.encode()}" +
                                "&description=${article.description?.encode() ?: ""}" +
                                "&urlToImage=${article.urlToImage?.encode() ?: ""}" +
                                "&url=${article.url.encode()}" +
                                "&content=${article.content?.encode() ?: ""}"
                    )
                })
            }

            composable(Screen.Favorites.route) {
                FavoriteScreen(viewModel = viewModel, onArticleClick = { article ->
                    navController.navigate(
                        "news_detail" +
                                "?title=${article.title.encode()}" +
                                "&description=${article.description?.encode() ?: ""}" +
                                "&urlToImage=${article.urlToImage?.encode() ?: ""}" +
                                "&url=${article.url.encode()}" +
                                "&content=${article.content?.encode() ?: ""}"
                    )
                })
            }

            composable(
                "news_detail?title={title}&description={description}&urlToImage={urlToImage}&url={url}&content={content}",
                    arguments = listOf(
                        navArgument("title"){type=NavType.StringType},
                        navArgument("description"){type=NavType.StringType ; nullable = true},
                        navArgument("urlToImage"){type=NavType.StringType ; nullable =true},
                        navArgument("url"){type=NavType.StringType},
                        navArgument("content"){type=NavType.StringType ; nullable = true}
                    )
                ){backStackEntry ->

                val title = backStackEntry.arguments?.getString("title")?.decode() ?: ""
                val description = backStackEntry.arguments?.getString("description")?.decode()
                val urlToImage = backStackEntry.arguments?.getString("urlToImage")?.decode()
                val url = backStackEntry.arguments?.getString("url")?.decode() ?: ""
                val content = backStackEntry.arguments?.getString("content")?.decode()

                if (title.isNotEmpty()){
                    val article = Article(
                        title=title,
                        description=description,
                        urlToImage=urlToImage,
                        url=url,
                        content = content
                    )
                    NewsDetailScreen(article = article, onBackClick = {navController.popBackStack()})
                }else{
                    Text("error ro load", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}