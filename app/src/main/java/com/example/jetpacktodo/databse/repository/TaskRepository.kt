package com.example.jetpacktodo.databse.repository

import androidx.lifecycle.LiveData
import com.example.jetpacktodo.databse.TaskDao
import com.example.jetpacktodo.databse.TaskEntity
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao:TaskDao
) {

    val readAllTask : LiveData<List<TaskEntity>> = taskDao.getAllTask()

    suspend fun addTask(taskEntity: TaskEntity) {
        taskDao.insertTask(task = taskEntity)
    }
}