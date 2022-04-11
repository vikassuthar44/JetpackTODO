package com.example.jetpacktodo.ui.profile

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpacktodo.R
import com.example.jetpacktodo.ui.widget.CustomToggleButton
import androidx.compose.foundation.lazy.items
import com.example.jetpacktodo.ui.theme.newBackground
import com.example.jetpacktodo.ui.widget.TopBarView


class ProfileScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.newBackground
            ) {
                ProfileMainScreen(this)
            }
        }
    }
}

@Composable
fun ProfileMainScreen(context: Context?) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        color = MaterialTheme.colors.newBackground,

        ) {
        Scaffold(
            modifier = Modifier.padding(all = 16.dp),
            backgroundColor = MaterialTheme.colors.newBackground,
            topBar = {
                TopBarView(context, "Profile",false)
            },
            content = {
                BodyContentView()
            }
        )
    }
}

@Composable
fun BodyContentView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        ProfileView()
        Spacer(modifier = Modifier.height(20.dp))
        Project()
        SettingTogles()

    }
}

@Composable
fun SettingTogles() {
    val profileSettingData = ProfileSettingDataSource.profileSettingDatas()
    LazyColumn{
        items(profileSettingData) { profileData ->
            SingleTogle(profileData)
        }
    }
}

@Composable
fun SingleTogle(settingData:ProfileSettingData) {
    val switchValue = remember {
        mutableStateOf(settingData.checked)
    }
    Surface(
        shape = RoundedCornerShape(size = 10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 10.dp, end = 10.dp),
        color = Color.White
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(
                    Color.White
                    /*Brush.horizontalGradient(
                        colors = listOf(
                            colorResource(id = R.color.light_yellow),
                            colorResource(id = R.color.purple_700)
                        )
                    )*/
                )
                .fillMaxWidth()
                .padding(all = 20.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(20),
                    color = colorResource(id = settingData.bakcgroundColor)
                ) {
                    Image(painter = painterResource(id = settingData.icons),
                        contentDescription = "image",
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = settingData.title,
                        style = MaterialTheme.typography.button.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                }

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.width(100.dp)
            ) {
                CustomToggleButton(
                    onTitle = "ON",
                    offTitle = "OFF",
                    toggleBgColor = colorResource(id = settingData.toggleColor),
                    checked = switchValue.value,
                    onCheckedValue ={
                    switchValue.value = it
                } )
            }

        }
    }

}

@Composable
fun Project() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(start = 7.dp, end = 7.dp)
            .fillMaxWidth()
            .padding(all = 5.dp)
    ) {
        SingleProject("Task Done","213")
        Spacer(modifier = Modifier.fillMaxWidth(fraction = 0.1f))
        SingleProject("Task Pending","213")
    }
}

@Composable
fun SingleProject(title:String, number:String) {
    Surface(
        shape = RoundedCornerShape(size = 10.dp),
        modifier = Modifier
            .width(150.dp)
            .clickable { }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    Color.White
                    /* Brush.horizontalGradient(
                        colors = listOf(
                            colorResource(id = R.color.light_yellow),
                            colorResource(id = R.color.purple_700)
                        )
                    )*/
                )
                .padding(all = 10.dp)
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = title,
                    maxLines = 1,
                    style = MaterialTheme.typography.button.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = number,
                    style = MaterialTheme.typography.button.copy(fontSize = 12.sp, color =Color.Gray, fontWeight = FontWeight.Bold)
                )

            }
        }
    }
}

@Composable
fun ProfileView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            shape = CircleShape,
            elevation = 5.dp,
            modifier = Modifier.size(100.dp),
            backgroundColor = colorResource(id = R.color.profile_bg_color)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "",
                contentScale = ContentScale.Fit
                )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Vikas Suthar",
            style = MaterialTheme.typography.button.copy(fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = "Android Developer",
                style = MaterialTheme.typography.button.copy(fontSize = 16.sp)
            )
        }

    }
}


@Preview
@Composable
fun MainScreenPreview() {
    ProfileMainScreen(null)
}


/*
@Composable
fun CustomSwitch2(
    scale: Float = 2f,
    width: Dp = 36.dp,
    height: Dp = 20.dp,
    strokeWidth: Dp = 2.dp,
    checkedTrackColor: Color = Color(0xFF35898F),
    uncheckedTrackColor: Color = Color(0xFFe0e0e0),
    gapBetweenThumbAndTrackEdge: Dp = 4.dp
) {
    val switchON = remember {
        mutableStateOf(true)
    }
    val thumbRadius = (height / 2) - gapBetweenThumbAndTrackEdge
    val animatePosition = animateFloatAsState(
        targetValue = if (switchON.value)
            with(LocalDensity.current) { (width - thumbRadius - gapBetweenThumbAndTrackEdge).toPx() }
        else
            with(LocalDensity.current) { (thumbRadius + gapBetweenThumbAndTrackEdge).toPx() }
    )
    Canvas(
        modifier = Modifier
            .size(width = width, height = height)
            .scale(scale = scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        // This is called when the user taps on the canvas (which is nothing but switch)
                        switchON.value = !switchON.value
                    }
                )
            }
    ) {
        // Track
        drawRoundRect(
            color = if (switchON.value) checkedTrackColor else uncheckedTrackColor,
            cornerRadius = CornerRadius(x = 10.dp.toPx(), y = 10.dp.toPx()),
            style = Stroke(width = strokeWidth.toPx())
        )
        // Thumb
        drawCircle(
            color = if (switchON.value) checkedTrackColor else uncheckedTrackColor,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = animatePosition.value,
                y = size.height / 2
            )
        )
    }

}*/
