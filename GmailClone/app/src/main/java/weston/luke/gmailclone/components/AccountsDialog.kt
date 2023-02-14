package weston.luke.gmailclone.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import weston.luke.gmailclone.R

@Composable
fun AccountsDialog(openDialog: MutableState<Boolean>) {
    Dialog(onDismissRequest = { openDialog.value = false }) {
        AccountsDialogUI()
    }
}

@Composable
fun AccountsDialogUI(modifier: Modifier = Modifier) {
    Card() {
        Column(
            modifier
                .background(Color.White)
                .padding(bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "")
                }
                Image(
                    painter = painterResource(id = R.drawable.download),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .weight(2.0f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.holly),
                    contentDescription = "Profile",
                    modifier = modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color = Color.Gray)
                )
                //Weight can be any value (as no other items have a weight),
                // because its the only item to have a weight it will fill the rest of the column
                //If two elements have weights, itll fill the column in ratios
                //Example weight 2 and weight 1, 2 will fill 2/3s 1 will fill 1/3
                Column(
                    Modifier
                        .weight(0.3f)
                        .padding(start = 16.dp, bottom = 16.dp)
                ) {
                    Text(text = "Holly Dog", fontWeight = FontWeight.SemiBold)
                    Text(text = "holly@dog.com")

                }
                Text(text = "99+", modifier = Modifier.padding(end = 16.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Card(
                    modifier = Modifier.requiredWidth(150.dp),
                    shape = RoundedCornerShape(50.dp),
                    border = BorderStroke(1.dp, color = Color.Gray)
                ) {
                    Text(
                        text = "Google Account",
                        modifier = modifier.padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.width(10.dp))
            }
            Divider(Modifier.padding(top = 16.dp))
        }
    }
}

@Preview
@Composable
fun AccountDialogUIPreview() {
    AccountsDialogUI()
}
