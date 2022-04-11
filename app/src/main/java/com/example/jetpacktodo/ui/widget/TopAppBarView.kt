package com.example.jetpacktodo.ui.widget

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpacktodo.R
import com.example.jetpacktodo.ui.profile.ProfileScreen

@Preview(showBackground = true)
@Composable
fun TopBarViewPreview() {
    TopBarView(context = null, pageTitle = "Top App Bar", true)
}

@Composable
fun TopBarView(context: Context?, pageTitle: String, isProfile: Boolean) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(end = 10.dp, bottom = 10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = "",
            modifier = Modifier
                .clickable {

                }
                .padding(all = 5.dp)
        )
        Text(
            text = pageTitle,
            style = MaterialTheme.typography.button.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
        )

        if (isProfile) {
            Card(
                shape = RoundedCornerShape(size = 10.dp),
                elevation = 2.dp,
                backgroundColor = colorResource(id = R.color.profile_bg_color),
                modifier = Modifier
                    .padding(top = 10.dp)
                    .size(50.dp)
                    .clickable {
                        context?.startActivity(Intent(context, ProfileScreen::class.java))
                    }
            ) {

                Image(
                    painter = painterResource(id = R.drawable.profile),
                    modifier = Modifier.align(Alignment.Bottom),
                    contentDescription = "profile",
                    contentScale = ContentScale.Fit
                )
            }

        } else {
            Card(
                shape = RoundedCornerShape(size = 5.dp),
                elevation = 5.dp,
                backgroundColor = Color.White,
                modifier = Modifier.clickable { }
            ) {
                Image(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "",
                    modifier = Modifier.padding(all = 5.dp)
                )
            }

        }
    }

}