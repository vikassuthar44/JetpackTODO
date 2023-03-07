package com.example.jetpacktodo.databse.repository

import androidx.lifecycle.LiveData
import com.example.jetpacktodo.databse.TaskDao
import com.example.jetpacktodo.databse.TaskEntity
import com.example.jetpacktodo.databse.category.CategoryEntity
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao:TaskDao
) {

    suspend fun addTask(taskEntity: TaskEntity) {
        taskDao.insertTask(task = taskEntity)
    }

    suspend fun readAllTask() : List<TaskEntity> {
        return taskDao.getAllTask()
    }

    suspend fun getCategoryWise(categoryEntity: CategoryEntity)  {
        //taskDao.getCategoryWise()
    }
}