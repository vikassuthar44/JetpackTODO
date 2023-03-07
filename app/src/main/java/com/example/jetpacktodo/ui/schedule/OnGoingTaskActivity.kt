package com.example.jetpacktodo.ui.schedule

import android.content.Context
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpacktodo.ui.theme.JetpackTODOTheme
import com.example.jetpacktodo.ui.theme.newBackground
import com.example.jetpacktodo.ui.widget.TopBarView
import com.example.jetpacktodo.R
import com.example.jetpacktodo.databse.TaskEntity
import com.example.jetpacktodo.ui.task.OnGoingTask
import com.example.jetpacktodo.ui.task.Task
import com.example.jetpacktodo.ui.task.TaskDataSource

class OnGoingTaskActivity: ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackTODOTheme {
                Surface(color = MaterialTheme.colors.newBackground) {
                    ScheduleMainScreen(context = this)
                }
            }
        }
    }
}

@Composable
fun ScheduleMainScreen(context: Context?, viewModel: ScheduleViewModel = hiltViewModel()) {
    Surface(
        color = MaterialTheme.colors.newBackground,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.padding(all = 16.dp),
            backgroundColor = MaterialTheme.colors.newBackground,
            topBar = {
                TopBarView(context, "Schedule",true)
            },
            content = {
                BodyContent(context,viewModel)
            }
        )
    }
}

@Composable
fun BodyContent(context: Context?, viewModel: ScheduleViewModel) {
    Column(
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(20.dp))
        MonthHeading()
        Spacer(modifier = Modifier.height(20.dp))
        DateHorizontalView()
        Spacer(modifier = Modifier.height(30.dp))
        SingleLine(70.dp, true, colorResource(id = R.color.purple_500))
        Spacer(modifier = Modifier.height(20.dp))
        OngoingTask(viewModel)
        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
fun OngoingTask(viewModel: ScheduleViewModel) {
    val ongoingTaskList = TaskDataSource.getOnGoingTask()
    val taskList = viewModel.taskList.observeAsState(arrayListOf())
    LazyColumn {
        items(taskList.value) { singleOnGoinTask ->
            SingleOnGoingTask(singleTask = singleOnGoinTask)
        }
    }
}

@Composable
fun SingleOnGoingTask(singleTask: TaskEntity) {
    Column(
        verticalArrangement = Arrangement.Top
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = singleTask.taskStartDate,
                        style = MaterialTheme.typography.button.copy(fontSize = 16.sp),
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    modifier = Modifier.padding(all = 10.dp)
                ) {
                    Text(
                        text = singleTask.taskTitle,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.button.copy(
                            color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = singleTask.taskEndTime + "-" + singleTask.taskEndTime,
                            style = MaterialTheme.typography.button.copy(fontSize = 16.sp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        SingleLine(paddingValuesStart = 20.dp, isCircle = false, Color.Gray)
    }
}

@Composable
fun SingleLine(paddingValuesStart: Dp, isCircle:Boolean, color: Color) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(start = paddingValuesStart),
        color = colorResource(id = R.color.background)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically) {
            if(isCircle) {
                Surface(
                    shape = CircleShape,
                    modifier = Modifier
                        .height(8.dp)
                        .width(8.dp),
                    color = colorResource(id = R.color.purple_500)
                ) {}
            }
            Surface(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
                color = color
            ){}
        }

    }
}
@Composable
fun DateHorizontalView() {
    val dateList:List<Date> = DateDatasource.getDateList()
    LazyRow {
        items(dateList) { singleDate ->
            SingleDateView(singleDate)
        }
    }
}

@Composable
fun SingleDateView(singleDate: Date) {
    var isSelect by remember {
        mutableStateOf(singleDate.isSelected)
    }
    Surface(
        shape = RoundedCornerShape(50),
        elevation = 5.dp,
        modifier = Modifier
            .height(140.dp)
            .width(90.dp)
            .padding(horizontal = 10.dp),
        color = if(isSelect) colorResource(id = R.color.purple_500) else Color.White

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    isSelect = !isSelect
                    singleDate.isSelected = isSelect
                }
        ) {
            Text(
                text = singleDate.date,
                style = MaterialTheme.typography.button.copy(
                    color = if(isSelect) Color.White else Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
            )

            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = singleDate.day,
                style = MaterialTheme.typography.button.copy(
                    color = if(isSelect) Color.White else Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }
    }
}

@Composable
fun MonthHeading() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Aug 6",
            style = MaterialTheme.typography.button.copy(
                color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 22.sp
            )
        )

        Surface(
            shape = RoundedCornerShape(50),
            color = Color.Gray
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    shape = CircleShape,
                    color = colorResource(id = R.color.purple_500)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "",
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "August",
                    style = MaterialTheme.typography.button.copy(
                        color = Color.Black, fontWeight = FontWeight.Normal, fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.width(15.dp))
                Surface(
                    shape = CircleShape,
                    color = colorResource(id = R.color.purple_500)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_forward),
                        contentDescription = "",
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    ScheduleMainScreen(context = null)
}