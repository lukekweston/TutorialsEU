package weston.luke.newsapp.ui

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import weston.luke.newsapp.ui.screen.DetailScreen
import weston.luke.newsapp.ui.screen.TopNews

@Composable
fun NewsApp() {
    Navigation()
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    NavHost(navController = navController, startDestination = "TopNews") {
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
                scrollState = scrollState
            )
        }
    }
}