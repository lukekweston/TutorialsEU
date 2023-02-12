package weston.luke.dogprofilepage


import android.graphics.fonts.FontStyle
import android.provider.ContactsContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProfilePage() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.holly),
            contentDescription = "Holly",
            modifier = Modifier
                .size(150.dp)
                .clip(shape = CircleShape)
                .border(width = 2.dp, color = Color.Red, shape = CircleShape),
            contentScale = ContentScale.Crop,
        )
        Text(text = "Holly dog", fontWeight = FontWeight.Bold)
        Text(text = "New Zealand")

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            ProfileStats(count = "150", title = "Followers")
            ProfileStats(count = "100", title = "Following")
            ProfileStats(count = "20", title = "Posts")
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Follow user")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Direct message")
            }
        }


    }
}


@Composable
fun ProfileStats(count: String, title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, fontWeight = FontWeight.Bold)
        Text(text = title)

    }
}


@Preview(showBackground = true)
@Composable
fun ProfilePagePreview() {
    ProfilePage()
}