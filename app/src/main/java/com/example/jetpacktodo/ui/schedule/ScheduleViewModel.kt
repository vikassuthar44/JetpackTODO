package com.example.jetpacktodo.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.jetpacktodo.databse.TaskEntity
import com.example.jetpacktodo.databse.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val taskRepository: TaskRepository
):ViewModel() {

    fun getTodayGoingTask() : LiveData<List<TaskEntity>> {
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_MONTH).toString() + " " + calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.YEAR)
        val list =  (taskRepository.readAllTask).apply {
            this.value?.map {
                if(it.taskStartDate == today ) {

                }
            }
        }

        return list
    }
}