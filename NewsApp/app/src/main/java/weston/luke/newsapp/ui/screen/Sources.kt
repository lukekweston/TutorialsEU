package weston.luke.newsapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import weston.luke.newsapp.R
import weston.luke.newsapp.data.models.TopNewsArticle
import weston.luke.newsapp.network.NewsManager

@Composable
fun Sources(newsManager: NewsManager) {

    val items = listOf(
        "TechCrunch" to "techcrunch",
        "TalkSport" to "talksport",
        "Business insider" to "business-insider",
        "Reuters" to "reuters",
        "Politico" to "politico",
        "TheVerge" to "the-verge"
    )

    Scaffold(topBar = {
        //Add top bar and set its text
        TopAppBar(title = { Text(text = "${newsManager.sourceName.value} Source") },
//      set the actions on the topbar
            actions = {
//            Variable for keeping track if the menu is or isnt expanded
                var menuExpanded by remember { mutableStateOf(false) }
//            Create an icon button on the topBar, this sets the menu expaded flag to true, the icon for this is more vert, 3 vertical dots
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "")
                }
//            Drop down menu, is expanded when menu expanded is true
                MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }) {
                        //Populate the menu
                        items.forEach { it ->
                            //Add a menu item
                            DropdownMenuItem(onClick = {
                                //Set the source to the clicked items value
                                newsManager.sourceName.value = it.second
                                //Close the Ui
                                menuExpanded = false
                            }) {
                                //Display items key
                                Text(it.first)
                            }
                        }
                    }
                }
            })
    }
    ) {

        newsManager.getArticleSource()
        val articles = newsManager.getArticleSource.value
        SourceContent(articles = articles.articles ?: listOf())

    }
}

@Composable
fun SourceContent(articles: List<TopNewsArticle>) {

    val uriHandler = LocalUriHandler.current

    LazyColumn {
        items(articles) { article ->
            val annotatedString = buildAnnotatedString {
                pushStringAnnotation(
                    tag = "URL",
                    annotation = article.url ?: "newsapi.org"
                )
                withStyle(
                    style = SpanStyle(
                        color = colorResource(id = R.color.purple_500),
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Read Full Article Here")
                }
            }

            Card(
                backgroundColor = colorResource(id = R.color.purple_700),
                elevation = 6.dp,
                modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .height(200.dp)
                        .padding(end = 8.dp, start = 8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = article.title ?: "Not Available",
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = article.description ?: "Not Available",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Card(backgroundColor = Color.White, elevation = 6.dp) {
                        ClickableText(
                            text = annotatedString,
                            modifier = Modifier.padding(8.dp),
                            onClick = {
                                //Magic to use the annotated string to go to a url
                                annotatedString.getStringAnnotations(it, it).firstOrNull()
                                    ?.let { result ->
                                        if (result.tag == "URL") {
                                            uriHandler.openUri(result.item)
                                        }
                                    }
                            })

                    }
                }


            }

        }
    }
}