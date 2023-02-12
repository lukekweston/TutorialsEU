package weston.luke.dogprofilepage


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfilePage() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.snapchat_490394838),
            contentDescription = "Holly"
        )
        Text(text="Holly dog")
        Text(text="New Zealand")
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePagePreview(){
    ProfilePage()
}