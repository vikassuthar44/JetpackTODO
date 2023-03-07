package com.example.jetpacktodo.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpacktodo.databse.TaskEntity
import com.example.jetpacktodo.databse.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _taskList = MutableLiveData<List<TaskEntity>>()
    val taskList: LiveData<List<TaskEntity>> = _taskList

    init {
        getTodayGoingTask()
    }

    private fun getTodayGoingTask() {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val today = calendar.get(Calendar.DAY_OF_MONTH)
                .toString() + " " + calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.YEAR)
            val list = (taskRepository.readAllTask()).apply {
                this.map {
                    it.taskStartDate == today
                }
            }

            _taskList.value = list
        }
    }
}