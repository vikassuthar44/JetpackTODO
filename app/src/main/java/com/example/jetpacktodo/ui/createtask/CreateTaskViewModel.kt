package com.example.jetpacktodo.ui.createtask

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpacktodo.Utils
import com.example.jetpacktodo.databse.TaskEntity
import com.example.jetpacktodo.databse.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    val taskTitle = MutableLiveData("")
    val taskDescription = MutableLiveData("")
    val taskStartDate = MutableLiveData("02 April 2022")
    val taskStartDateLiveData:LiveData<String> = taskStartDate
    val taskEndDate = MutableLiveData("10 April 2022")
    val taskStartTime = MutableLiveData("11:30 PM")
    val taskEndTime = MutableLiveData("05:30 PM")
    val taskCategory = MutableLiveData("")

    fun onTaskTitleChanged(newTitle: String) {
        taskTitle.value = newTitle
    }

    fun onTaskDescriptionChanged(newDescription: String) {
        taskDescription.value = newDescription
    }

    fun onTaskStartDateChanged(newStartDate: String?) {
        taskStartDate.value = newStartDate
    }

    fun onTaskEndDateChanged(newEndDate: String?) {
        taskEndDate.value = newEndDate
    }

    fun onTaskStartTimeChanged(newStartTime: String?) {
        taskStartTime.value = newStartTime
    }

    fun onTaskEndTimeChanged(newEndTime: String?) {
        taskEndTime.value = newEndTime
    }

    fun onTaskCategoryChanged(newTaskCat: String) {
        taskCategory.value = newTaskCat
    }

    private var _taskList: MutableLiveData<List<TaskEntity>>? = null
    var tasklist: LiveData<List<TaskEntity>>? = _taskList

    fun insertTask() {
        val task = TaskEntity(
            taskTitle = taskTitle.value.toString(),
            taskStartDate = taskStartDate.value.toString(),
            taskEndDate = taskEndDate.value.toString(),
            taskStartTime = taskStartTime.value.toString(),
            taskEndTime = taskEndTime.value.toString(),
            taskCategory = taskCategory.value.toString(),
            taskDescription = taskDescription.value.toString(),
        )

        viewModelScope.launch {
            taskRepository.addTask(taskEntity = task)
        }

    }

    fun getAllData() {
        tasklist = taskRepository.readAllTask

        val taskLists: List<TaskEntity>? = tasklist!!.value

        try {
            if (taskLists?.size!! > 0) {
                for (item in taskLists) {
                    Log.d("CreateTaskViewModel", "getAllData: ${item.taskTitle}")
                }
            }
        } catch (e: Exception) {
            Log.d("CreateTaskViewModel", "getAllData: ${e.message}")
        }

    }

    fun printLogMessage(message: String) {
        Log.d("CreateTaskViewModel", "printLogMessage: $message")
    }

    fun getTaskManage(isDate: Boolean, isStart: Boolean): String {
        return if (isDate) {
            if (isStart) {
                taskStartDate.value.toString()
            } else {
                taskEndDate.value.toString()
            }
        } else {
            if (isStart) {
                taskStartTime.value.toString()
            } else {
                taskEndTime.value.toString()
            }
        }
    }

}