package weston.luke.newsapp.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
fun NewsApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    MainScreen(navController = navController, scrollState = scrollState, mainViewModel = viewModel)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState, mainViewModel: MainViewModel) {
    Scaffold(bottomBar = {
        BottomMenu(navController = navController)
    }) {
        Navigation(
            navController = navController,
            scrollState = scrollState,
            paddingValues = it,
            viewModel = mainViewModel
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
    val topArticles = viewModel.topNewsResponse.collectAsState().value.articles
    //Set the articles to all articles from newsResponse endpoint
    articles.addAll(
        topArticles ?: listOf()
    )
    Log.d("news", "$articles")
    NavHost(
        navController = navController,
        startDestination = BottomMenuScreen.TopNews.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        val queryState = mutableStateOf(viewModel.query.value)
        bottomNavigation(navController = navController, articles, queryState, viewModel)

        composable(
            "DetailScreen/{index}",
            //1) get the news id from the path, convert this to an int as we knot the id passed will be an int
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {
                val article = articles[index]
                //If there is a search, set the items to the searched items
                if (queryState.value != "") {
                    articles.clear()
                    articles.addAll(viewModel.getSearchedResponse.value.articles ?: listOf())
                }
                //If there isnt a search set the items to all the news items
                else{
                    articles.clear()
                    articles.addAll(viewModel.topNewsResponse.value.articles ?: listOf())
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
    query: MutableState<String>,
    viewModel: MainViewModel
) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(
            navController = navController,
            articles = articles,
            query = query,
            viewModel = viewModel
        )
    }
    composable(BottomMenuScreen.Categories.route) {
        //Default category when first navigated to
        viewModel.getArticlesByCategory("business")
        viewModel.selectedCategoryChanged("business")

        Categories(viewModel = viewModel, onFetchCategory = {
            viewModel.selectedCategoryChanged(it)
            viewModel.getArticlesByCategory(it)
        })
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources(viewModel = viewModel)
    }
}