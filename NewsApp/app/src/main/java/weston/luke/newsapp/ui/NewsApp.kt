package weston.luke.newsapp.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import weston.luke.newsapp.MockData
import weston.luke.newsapp.components.BottomMenu
import weston.luke.newsapp.BottomMenuScreen
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
        Navigation(navController = navController, scrollState = scrollState)
    }
}

@Composable
fun Navigation(navController: NavHostController, scrollState: ScrollState) {
    NavHost(navController = navController, startDestination = "TopNews") {
        bottomNavigation(navController = navController)
        composable("TopNews") {
            TopNews(navController = navController)
        }

        composable(
            "DetailScreen/{newsId}",
            //1) get the news id from the path, convert this to an int as we knot the id passed will be an int
            arguments = listOf(navArgument("newsId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            //2) Navigate to detail screen passing in the newsData, this is retrieved from MockData.getNews() using the
            //Id passed into the path - we get this with navBackStackEntry.arguments?.getInt("newsId")
            DetailScreen(
                newsData = MockData.getNews(newsId = navBackStackEntry.arguments?.getInt("newsId")),
                scrollState = scrollState,
                navController = navController
            )
        }
    }
}

fun NavGraphBuilder.bottomNavigation(navController: NavController) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController)
    }
    composable(BottomMenuScreen.Categories.route) {
        Categories()
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources()
    }
}