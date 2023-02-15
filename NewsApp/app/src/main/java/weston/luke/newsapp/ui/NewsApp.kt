package weston.luke.newsapp.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import weston.luke.newsapp.MockData
import weston.luke.newsapp.components.BottomMenu
import weston.luke.newsapp.BottomMenuScreen
import weston.luke.newsapp.models.TopNewsArticle
import weston.luke.newsapp.network.NewsManager
import weston.luke.newsapp.ui.screen.Categories
import weston.luke.newsapp.ui.screen.DetailScreen
import weston.luke.newsapp.ui.screen.Sources
import weston.luke.newsapp.ui.screen.TopNews

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    MainScreen(navController, scrollState)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState) {
    Scaffold(bottomBar = {
        BottomMenu(navController = navController)
    }) {
        Navigation(
            navController = navController,
            scrollState = scrollState,
            paddingValues = it
        )
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    newsManager: NewsManager = NewsManager(),
    paddingValues: PaddingValues
) {
    val articles = newsManager.newsResponse.value.articles
    Log.d("news", "$articles")
    articles?.let {
        NavHost(
            navController = navController,
            startDestination = BottomMenuScreen.TopNews.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            bottomNavigation(navController = navController, articles)

            composable(
                "DetailScreen/{index}",
                //1) get the news id from the path, convert this to an int as we knot the id passed will be an int
                arguments = listOf(navArgument("index") { type = NavType.IntType })
            ) { navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let{
                    val article = articles[index]
                    DetailScreen(
                        article = article,
                        scrollState = scrollState,
                        navController = navController
                    )
                }
            }
        }
    }
}

fun NavGraphBuilder.bottomNavigation(navController: NavController, articles: List<TopNewsArticle>) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController, articles = articles)
    }
    composable(BottomMenuScreen.Categories.route) {
        Categories()
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources()
    }
}