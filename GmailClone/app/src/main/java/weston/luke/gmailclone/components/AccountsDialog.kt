package weston.luke.gmailclone.components


import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import weston.luke.gmailclone.R
import weston.luke.gmailclone.accountList
import weston.luke.gmailclone.model.Account

@Composable
fun AccountsDialog(openDialog: MutableState<Boolean>) {
    Dialog(onDismissRequest = { openDialog.value = false },
    properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        AccountsDialogUI(openDialog = openDialog)
    }
}

@Composable
fun AccountsDialogUI(modifier: Modifier = Modifier, openDialog: MutableState<Boolean>) {
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
                IconButton(onClick = { openDialog.value = false}) {
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
            AccountItem(account = accountList[0])
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
            Column() {
                for (account in accountList.drop(1)) {
                    AccountItem(account)
                }
            }
            Divider(Modifier.padding(top = 12.dp, bottom = 12.dp))
            AddAccount(icon = Icons.Default.PersonAddAlt, title = "Add another account")
            AddAccount(
                icon = Icons.Outlined.ManageAccounts,
                title = "Manage accounts on this device"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "Privacy Policy")
                Card(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .size(3.dp),
                    shape = CircleShape,
                    backgroundColor = Color.Black
                ){

                }
                Text(text = "Terms of service")
            }
        }
    }
}

@Composable
fun AddAccount(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
        )
    }
}

@Composable
fun AccountItem(account: Account) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp)
    ) {
        if (account.icon != null) {
            Image(
                painter = painterResource(id = account.icon),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color = Color.Gray)
            )
        } else {
            Card(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(40.dp)
                    .clip(CircleShape),
                backgroundColor = Color.Gray
            ) {
                Text(
                    text = account.username[0].uppercaseChar().toString(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Column(
            //Weight can be any value (as no other items have a weight),
            // because its the only item to have a weight it will fill the rest of the column
            //If two elements have weights, itll fill the column in ratios
            //Example weight 2 and weight 1, 2 will fill 2/3s 1 will fill 1/3
            Modifier
                .weight(0.3f)
                .padding(start = 16.dp, bottom = 16.dp)
        ) {
            Text(text = account.username, fontWeight = FontWeight.SemiBold)
            Text(text = account.email)

        }
        if (account.unreadEmails > 99) {
            Text(text = "99+", modifier = Modifier.padding(end = 16.dp))
        } else {
            Text(text = "${account.unreadEmails}", modifier = Modifier.padding(end = 16.dp))
        }

    }

}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun AccountDialogUIPreview() {
    AccountsDialogUI(openDialog = mutableStateOf(false))
}
