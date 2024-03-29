package weston.luke.newsapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.coil.CoilImage
import weston.luke.newsapp.data.MockData
import weston.luke.newsapp.data.MockData.getTimeAgo
import weston.luke.newsapp.data.models.TopNewsArticle
import weston.luke.newsapp.R
import weston.luke.newsapp.components.ErrorUi
import weston.luke.newsapp.components.LoadingUi
import weston.luke.newsapp.components.SearchBar
import weston.luke.newsapp.network.NewsManager
import weston.luke.newsapp.ui.MainViewModel


@Composable
fun TopNews(
    navController: NavController,
    articles: List<TopNewsArticle>,
    query: MutableState<String>,
    viewModel: MainViewModel,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        // Text(text = "Top News", fontWeight = FontWeight.SemiBold)
        SearchBar(query = query, viewModel = viewModel)

        val resultList = mutableListOf<TopNewsArticle>()
        val searchedText = query.value
        if (searchedText != "") {
            resultList.addAll(
                viewModel.getSearchedResponse.collectAsState().value.articles ?: articles
            )
        } else {
            resultList.addAll(articles)
        }
        //If loading, add the loading ui, if error add the error ui, else will be loaded with no error so add and display news items
        when {
//            viewModel.isLoading.collectAsState().value
            isLoading.value -> {
                LoadingUi()
            }
            isError.value -> {
                ErrorUi()
            }
            else -> {
                LazyColumn {
                    items(resultList.size) { index ->
                        TopNewsItem(article = resultList[index],
                            onNewsClick = {
                                navController.navigate("DetailScreen/$index")
                            })
                    }
                }
            }
        }
    }
}


@Composable
fun TopNewsItem(article: TopNewsArticle, onNewsClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .padding(8.dp)
            .clickable { onNewsClick() }
    ) {

        CoilImage(
            imageModel = article.urlToImage,
            contentScale = ContentScale.Crop,
            error = ImageBitmap.imageResource(id = R.drawable.breaking_news),
            placeHolder = ImageBitmap.imageResource(id = R.drawable.breaking_news)
        )

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = MockData.stringToDate(article.publishedAt!!).getTimeAgo(),
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(80.dp))
            Text(text = article.title!!, color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {

    TopNewsItem(
        TopNewsArticle(
            author = "Thomas Barrabi",
            title = "Sen. Murkowski slams Dems over 'show votes' on federal election bills - Fox News",
            description = "Sen. Lisa Murkowski, R-Alaska, slammed Senate Democrats for pursuing “show votes” on federal election bills on Wednesday as Republicans used the filibuster to block consideration the John Lewis Voting Rights Advancement Act.",
            publishedAt = "2021-11-04T01:57:36Z"
        )
    )
}