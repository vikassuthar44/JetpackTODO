package com.example.jetpacktodo.ui.task

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpacktodo.R
import com.example.jetpacktodo.databse.TaskEntity
import com.example.jetpacktodo.ui.bottomnavigation.BottomNavigationItem
import com.example.jetpacktodo.ui.createtask.CreateMainScreen
import com.example.jetpacktodo.ui.createtask.CreateTaskScreen
import com.example.jetpacktodo.ui.profile.ProfileMainScreen
import com.example.jetpacktodo.ui.profile.ProfileScreen
import com.example.jetpacktodo.ui.schedule.ScheduleMainScreen
import com.example.jetpacktodo.ui.theme.JetpackTODOTheme
import com.example.jetpacktodo.ui.theme.newBackground
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackTODOTheme {
                HomeMainScreen()
            }
        }
    }
}

@Composable
fun HomeMainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation1(navController = navController)
        }
    ) {
        NavigationGraph(navController = navController)
    }
}

@Composable
fun BottomNavigation1(navController: NavController) {
    val bottomList = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Task,
        BottomNavigationItem.Schedule,
        BottomNavigationItem.Profile,
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.purple_500)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        bottomList.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp
                    )
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(
                        item.screenRoute
                    ) {
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(
                                screenRoute
                            ) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationItem.Home.screenRoute
    ) {
        composable(BottomNavigationItem.Home.screenRoute) {
            MainScreen(context = LocalContext.current)
        }
        composable(BottomNavigationItem.Task.screenRoute) {
            CreateMainScreen(context = LocalContext.current)
        }
        composable(BottomNavigationItem.Schedule.screenRoute) {
            ScheduleMainScreen(context = LocalContext.current)
        }
        composable(BottomNavigationItem.Profile.screenRoute) {
            ProfileMainScreen(context = LocalContext.current)
        }
    }

}

@Composable
fun MainScreen(context: Context?, mainViewModel: MainViewModel = hiltViewModel()) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.newBackground,

        ) {
        Scaffold(
            modifier = Modifier.padding(top = 16.dp, bottom = 50.dp),
            backgroundColor = MaterialTheme.colors.newBackground,
            topBar = {
                AppTopBarView(context)
            },
            content = {
                BodyContent(mainViewModel)
            },
            floatingActionButton = {
                Card(
                    shape = CircleShape,
                    backgroundColor = Color.Gray,
                    elevation = 5.dp,
                    modifier = Modifier
                        .padding(all = 5.dp)
                        .clickable {
                            context?.startActivity(Intent(context, CreateTaskScreen::class.java))
                        }
                ) {
                    Image(
                        imageVector = Icons.Default.Add,
                        modifier = Modifier.padding(all = 10.dp),
                        contentDescription = "",
                    )
                }

            }
        )
    }

}

@Composable
fun BodyContent(mainViewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 20.dp)
    ) {
        SubTitle(subTitle = "My Task")
        TaskCategoryList()
        Spacer(modifier = Modifier.height(20.dp))
        SubTitle(subTitle = "On Going")
        Spacer(modifier = Modifier.height(20.dp))
        OnGoingTask(mainViewModel)
    }
}

@Composable
fun OnGoingTask(mainViewModel: MainViewModel) {
    //val onGoingTasks = mainViewModel.getAllOnGoingTask().observeAsState(arrayListOf())
    val onGoingTask: List<OnGoingTask> = TaskDataSource.getOnGoingTask()

    val onGoingTasks = mainViewModel.allTask.observeAsState(arrayListOf())
    LazyColumn {
        items(onGoingTasks.value) { onGoingTask ->
            SingleOnGoingTask(onGoingTask = onGoingTask)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SingleOnGoingTask(onGoingTask: TaskEntity) {
    val selected = remember {
        mutableStateOf(false)
    }
    val scale = animateFloatAsState(targetValue = if (selected.value) 0.9f else 1f)
    Surface(
        shape = RoundedCornerShape(size = 20.dp),
        color = Color.Transparent,
        modifier = Modifier
            .padding(all = 10.dp)
            .scale(scale = scale.value)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        selected.value = true
                    }
                    MotionEvent.ACTION_UP -> {
                        selected.value = false
                    }
                    else -> {
                        selected.value = false
                    }
                }
                true
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White
                    /*Brush.horizontalGradient(
                        colors = listOf(
                            colorResource(id = R.color.light_yellow),
                            colorResource(id = R.color.purple_700)
                        )
                    )*/
                )
                .clickable { }
                .padding(all = 16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(fraction = 0.7f)) {
                Text(
                    text = onGoingTask.taskTitle,
                    style = MaterialTheme.typography.button.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = onGoingTask.taskStartDate,
                        style = MaterialTheme.typography.button.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = " - "
                    )
                    Text(
                        text = onGoingTask.taskEndTime,
                        style = MaterialTheme.typography.button.copy(fontSize = 12.sp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(size = 5.dp))
                        .background(color = colorResource(id = R.color.profile_bg_color))
                        .padding(all = 5.dp)
                ) {
                    Text(
                        text = "Complete: ",
                        style = MaterialTheme.typography.button.copy(Color.Black)
                    )
                    Text(
                        text = "80%",
                        style = MaterialTheme.typography.button.copy(Color.Black)
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.tech),
                    contentDescription = "image",
                    modifier = Modifier

                        .size(80.dp)
                )
            }

        }

    }
}

@Composable
fun SubTitle(subTitle: String) {
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = subTitle,
            style = MaterialTheme.typography.button.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun TaskCategoryList() {
    val taskList: List<Task> = TaskDataSource.getTaskData()
    LazyRow {
        items(taskList) { task ->
            SingleTask(task)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SingleTask(task: Task) {
    val selected = remember {
        mutableStateOf(false)
    }
    val scale = animateFloatAsState(targetValue = if (selected.value) 0.9f else 1f)

    Surface(
        shape = RoundedCornerShape(size = 30.dp),
        modifier = Modifier
            .padding(top = 20.dp, end = 10.dp, start = 10.dp)
            .width(120.dp)
            .height(150.dp)
            .clickable { }
            .scale(scale = scale.value)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        selected.value = true
                    }
                    MotionEvent.ACTION_UP -> {
                        selected.value = false
                    }
                    else -> {
                        selected.value = false
                    }
                }
                true
            },
        color = Color.Transparent
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    Color.White
                    /*Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.light_yellow),
                            colorResource(id = R.color.purple_700)
                        )
                    )*/
                )
                .padding(bottom = 10.dp)
        ) {
            Image(
                painter = painterResource(id = task.taskIcon),
                contentDescription = "",
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 10.dp)
            )
            Text(
                text = task.title,
                style = MaterialTheme.typography.button.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = task.noOfTask,
                    style = MaterialTheme.typography.button.copy(fontSize = 12.sp)
                )

            }
        }

    }
}

@Composable
fun AppTopBarView(context: Context?) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = "Hey Vikas",
                style = MaterialTheme.typography.button.copy(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = "Mar 29, 2022",
                    style = MaterialTheme.typography.button.copy(fontSize = 16.sp)
                )
            }

        }

        Card(
            shape = RoundedCornerShape(size = 10.dp),
            elevation = 2.dp,
            backgroundColor = colorResource(id = R.color.profile_bg_color),
            modifier = Modifier
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

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeMainScreen()
}
