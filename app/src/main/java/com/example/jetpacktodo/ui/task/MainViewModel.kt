package com.example.jetpacktodo.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpacktodo.databse.TaskEntity
import com.example.jetpacktodo.databse.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {

    private val _allTaskLiveData = MutableLiveData<List<TaskEntity>>()
    val allTask: LiveData<List<TaskEntity>> = _allTaskLiveData
    init {
        getAllOnGoingTask()
    }

    private fun getAllOnGoingTask() {
        viewModelScope.launch {
            _allTaskLiveData.value =  taskRepository.readAllTask()
        }
    }
}