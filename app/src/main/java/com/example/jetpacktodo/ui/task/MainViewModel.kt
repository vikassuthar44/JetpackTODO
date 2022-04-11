package com.example.jetpacktodo.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.jetpacktodo.databse.TaskEntity
import com.example.jetpacktodo.databse.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {


    fun getAllOnGoingTask():LiveData<List<TaskEntity>> {
        return taskRepository.readAllTask
    }
}