package weston.luke.newsapp.ui.screen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
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

    }
}