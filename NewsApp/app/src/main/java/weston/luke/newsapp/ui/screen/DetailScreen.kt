package weston.luke.newsapp.ui.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.skydoves.landscapist.coil.CoilImage
import weston.luke.newsapp.data.MockData
import weston.luke.newsapp.R
import weston.luke.newsapp.data.MockData.getTimeAgo
import weston.luke.newsapp.models.TopNewsArticle


@Composable
fun DetailScreen(article: TopNewsArticle, scrollState: ScrollState, navController: NavController) {

    Scaffold(topBar = {
        DetailTopAppBar(onBackPressed = { navController.popBackStack() })

    }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold)
            CoilImage(
                imageModel = article.urlToImage,
                contentScale = ContentScale.Crop,
                error = ImageBitmap.imageResource(id = R.drawable.breaking_news),
                placeHolder = ImageBitmap.imageResource(id = R.drawable.breaking_news)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconWithInfo(icon = Icons.Default.Edit, info = article.author ?: "Not Available")
                IconWithInfo(
                    icon = Icons.Default.DateRange,
                    info = MockData.stringToDate(article.publishedAt!!).getTimeAgo()
                )

            }
            Text(
                text = article.title ?: "Not Available",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = article.description ?: "Not Available",
                modifier = Modifier.padding(top = 16.dp)
            )

        }
    }
}

@Composable
fun DetailTopAppBar(onBackPressed: () -> Unit = {}) {
    TopAppBar(title = { Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold) },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        })
}


//Make these as a seperate composable so that the spacing will be between the two IconWithInfo, not
// between all the items
@Composable
fun IconWithInfo(icon: ImageVector, info: String) {
    Row {
        Icon(
            icon, contentDescription = "Author", modifier = Modifier.padding(end = 8.dp),
            tint = colorResource(id = R.color.purple_500)
        )
        Text(text = info)
    }
}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        TopNewsArticle(
            author = "Thomas Barrabi",
            title = "Sen. Murkowski slams Dems over 'show votes' on federal election bills - Fox News",
            description = "Sen. Lisa Murkowski, R-Alaska, slammed Senate Democrats for pursuing “show votes” on federal election bills on Wednesday as Republicans used the filibuster to block consideration the John Lewis Voting Rights Advancement Act.",
            publishedAt = "2021-11-04T01:57:36Z"
        ), rememberScrollState(), rememberNavController()
    )
}


