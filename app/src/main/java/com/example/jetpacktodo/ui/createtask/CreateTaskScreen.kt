package com.example.jetpacktodo.ui.createtask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import com.example.jetpacktodo.R
import com.example.jetpacktodo.Utils
import com.example.jetpacktodo.ui.task.Task
import com.example.jetpacktodo.ui.task.TaskDataSource
import com.example.jetpacktodo.ui.theme.JetpackTODOTheme
import com.example.jetpacktodo.ui.theme.newBackground
import com.example.jetpacktodo.ui.widget.CustomButton
import com.example.jetpacktodo.ui.widget.CustomTextField
import com.example.jetpacktodo.ui.widget.TopBarView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class CreateTaskScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackTODOTheme {
                Surface(color = MaterialTheme.colors.newBackground) {
                    CreateMainScreen(context = this)
                }
            }
        }
    }

}

@Composable
fun CreateMainScreen(context: Context?, viewModel: CreateTaskViewModel = hiltViewModel()) {
    viewModel.printLogMessage("this is from class!")
    viewModel.getAllData()
    Surface(
        color = MaterialTheme.colors.newBackground,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.padding(all = 16.dp),
            backgroundColor = MaterialTheme.colors.newBackground,
            topBar = {
                TopBarView(context, "",false)
            },
            content = {

                BodyContent(context, viewModel)
            }
        )
    }
}


@Composable
fun BodyContent(context: Context?, viewModel: CreateTaskViewModel) {
    val verticalScrollState: ScrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = verticalScrollState, enabled = true)
    ) {
        Text(
            text = "Create \nNew Task",
            style = MaterialTheme.typography.button.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp
            )
        )

        Spacer(modifier = Modifier.height(20.dp))
        TaskTitle(viewModel = viewModel)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(20.dp))
        TaskManage(context, viewModel)
        Spacer(modifier = Modifier.height(20.dp))
        TaskDescription(viewModel)
        Spacer(modifier = Modifier.height(20.dp))
        Category(viewModel = viewModel)
        Spacer(modifier = Modifier.height(20.dp))
        CreateTaskButton(context)
        Spacer(modifier = Modifier.height(60.dp))

    }
}


@Composable
fun CreateTaskButton(context: Context?) {
    val viewModel: CreateTaskViewModel = hiltViewModel()
    CustomButton(
        buttonTitle = "Create Task"
    ) {
        viewModel.insertTask()
    }
}

@Composable
fun Category(viewModel: CreateTaskViewModel) {
    val category: List<Task> = TaskDataSource.getTaskData()
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = "Category",
            style = MaterialTheme.typography.button.copy(fontSize = 18.sp)
        )
    }

    Spacer(modifier = Modifier.height(10.dp))
    LazyRow {
        items(category) { singleCategory ->
            SingleCategory(singleCategory, viewModel = viewModel)
        }
    }
}


@Composable
fun SingleCategory(singleCategory: Task, viewModel: CreateTaskViewModel) {
    Surface(
        shape = RoundedCornerShape(size = 10.dp),
        modifier = Modifier
            .wrapContentWidth()
            .padding(end = 10.dp),
        color = colorResource(id = singleCategory.taskColor)
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = singleCategory.title,
                modifier = Modifier
                    .clickable {
                        viewModel.onTaskCategoryChanged(singleCategory.title)
                    }
                    .padding(all = 10.dp),
                style = MaterialTheme.typography.button.copy(
                    fontSize = 18.sp, color = colorResource(
                        id = singleCategory.taskTitleColor
                    ), fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun TaskDescription(viewModel: CreateTaskViewModel) {

    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = "Task Description",
            style = MaterialTheme.typography.button.copy(fontSize = 18.sp)
        )
    }

    Spacer(modifier = Modifier.height(10.dp))
    CustomTextField(
        modifier = Modifier
            .heightIn(min = 100.dp)
            .fillMaxWidth(),
        placeHolderTitle = "Task Description",
        initialValue = viewModel.taskDescription.value.toString(),
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
    ) { newTitle ->
        viewModel.onTaskDescriptionChanged(newTitle)
    }
}

@Composable
fun TaskManage(context: Context?, viewModel: CreateTaskViewModel) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxWidth()
    ) {

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = "Task Date",
                style = MaterialTheme.typography.button.copy(fontSize = 18.sp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            SingleTaskManage(context, "Task Start", true, viewModel, true)
            Spacer(modifier = Modifier.width(10.dp))
            SingleTaskManage(context, "Task end", true, viewModel, false)
        }
        Spacer(modifier = Modifier.height(10.dp))
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = "Task Time",
                style = MaterialTheme.typography.button.copy(fontSize = 18.sp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            SingleTaskManage(context, "Task Start", false, viewModel = viewModel, true)
            Spacer(modifier = Modifier.width(10.dp))
            SingleTaskManage(context, "Task End", false, viewModel = viewModel, false)
        }

    }
}

@Composable
fun SingleTaskManage(
    context: Context?,
    title: String,
    isDate: Boolean,
    viewModel: CreateTaskViewModel,
    isStart: Boolean
) {
    val mDate = remember {
        mutableStateOf(
            if(isDate) {
                if(isStart)
                    viewModel.taskStartDate.value.toString()
                else
                    viewModel.taskEndDate.value.toString()
            } else {
                if(isStart)
                    viewModel.taskStartTime.value.toString()
                else
                    viewModel.taskEndTime.value.toString()
            }
        )
    }
    Surface(
        shape = RoundedCornerShape(size = 10.dp),
        color = Color.White,
        modifier = Modifier.width(180.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.White)
                .clickable {
                    if (isDate) {
                        if (isStart) {
                            viewModel.onTaskStartDateChanged(context?.let {
                                getDate(
                                    it,
                                    viewModel.taskStartDate,
                                    mDate
                                )
                            })
                        } else {
                            viewModel.onTaskEndDateChanged(context?.let {
                                getDate(
                                    it,
                                    viewModel.taskEndDate,
                                    mDate
                                )
                            })
                        }
                    } else {
                        if (isStart) {
                            viewModel.onTaskStartTimeChanged(context?.let {
                                getTime(
                                    it,
                                    viewModel.taskStartTime,
                                    mDate
                                )
                            })
                        } else {
                            viewModel.onTaskEndTimeChanged(context?.let {
                                getTime(
                                    it,
                                    viewModel.taskEndTime,
                                    mDate
                                )
                            })
                        }
                    }

                }
                .padding(all = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_clock),
                contentDescription = "clock"
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.button.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = mDate.value,
                        style = MaterialTheme.typography.button.copy(
                            fontSize = 14.sp, color = colorResource(
                                id = R.color.purple_200
                            )
                        )
                    )
                }
            }
        }
    }

}

@Composable
fun TaskTitle(viewModel: CreateTaskViewModel) {

    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = "Task Title",
            style = MaterialTheme.typography.button.copy(fontSize = 18.sp)
        )
    }

    Spacer(modifier = Modifier.height(10.dp))
    CustomTextField(
        modifier = Modifier.fillMaxWidth(),
        placeHolderTitle = "Task Title",
        initialValue = viewModel.taskTitle.value.toString(),
        fontSize = 25.sp,
        fontWeight = FontWeight.ExtraBold,
        singleLine = true
    ) { newTitle ->
        viewModel.onTaskTitleChanged(newTitle = newTitle)
    }
}

fun getTime(context: Context, value: MutableLiveData<String>, currentValue:MutableState<String>): String? {
    val mcurrentTime = Calendar.getInstance()
    val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
    val minute = mcurrentTime[Calendar.MINUTE]
    val mTimePicker = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            value.value = Utils.getTime(selectedHour, selectedMinute)
            currentValue.value = Utils.getTime(selectedHour, selectedMinute)
        },
        hour,
        minute,
        false
    ) //Yes 12 hour time

    mTimePicker.setTitle("Select Time")
    mTimePicker.show()

    return value.value
}

fun getDate(context: Context, value: MutableLiveData<String>, currentValue:MutableState<String>): String? {


    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()


    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            value.value = "$mDayOfMonth ${Utils.getMonthName(mMonth)} $mYear"
            currentValue.value = "$mDayOfMonth ${Utils.getMonthName(mMonth)} $mYear"
        }, mYear, mMonth, mDay
    )

    mDatePickerDialog.show()
    return value.value
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CreateMainScreen(null)
}