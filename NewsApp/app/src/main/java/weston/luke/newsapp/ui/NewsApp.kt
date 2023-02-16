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
import weston.luke.newsapp.components.BottomMenu
import weston.luke.newsapp.BottomMenuScreen
import weston.luke.newsapp.data.models.TopNewsArticle
import weston.luke.newsapp.network.Api
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
fun MainScreen(navController: NavHostController, scrollState: ScrollState, viewModel: MainViewModel) {
    Scaffold(bottomBar = {
        BottomMenu(navController = navController)
    }) {
        Navigation(
            navController = navController,
            scrollState = scrollState,
            paddingValues = it,
            viewModel = viewModel
        )
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    newsManager: NewsManager = NewsManager(Api.retrofitService),
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {
    val articles = mutableListOf<TopNewsArticle>()
    //Set the articles to all articles from newsResponse endpoint
    articles.addAll(
        newsManager.newsResponse.value.articles ?: listOf<TopNewsArticle>()
    )
    Log.d("news", "$articles")
    NavHost(
        navController = navController,
        startDestination = BottomMenuScreen.TopNews.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        bottomNavigation(navController = navController, articles, newsManager)

        composable(
            "DetailScreen/{index}",
            //1) get the news id from the path, convert this to an int as we knot the id passed will be an int
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {
                val article = articles[index]
                //If there is a search, set the items to the searched items
                if (newsManager.query.value.isNotEmpty()) {
                    articles.clear()
                    articles.addAll(newsManager.searchedNewsResponse.value.articles ?: listOf())
                }
                //If there isnt a search set the items to all the news items
                else{
                    articles.clear()
                    articles.addAll(newsManager.newsResponse.value.articles ?: listOf())
                }

                DetailScreen(
                    article = article,
                    scrollState = scrollState,
                    navController = navController
                )
            }
        }
    }
}

fun NavGraphBuilder.bottomNavigation(
    navController: NavController,
    articles: List<TopNewsArticle>,
    newsManager: NewsManager
) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(
            navController = navController,
            articles = articles,
            query = newsManager.query,
            newsManager = newsManager
        )
    }
    composable(BottomMenuScreen.Categories.route) {
        //Default category when first navigated to
       // newsManager.onSelectedCategoryChanged("business")
        newsManager.getArticlesByCategory("business")

        Categories(newsManager = newsManager, onFetchCategory = {
            newsManager.onSelectedCategoryChanged(it)
            newsManager.getArticlesByCategory(it)
        })
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources(newsManager)
    }
}