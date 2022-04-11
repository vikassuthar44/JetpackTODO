package com.example.jetpacktodo.databse

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {

    @Query("SELECT * FROM create_task")
    fun getAllTask() : LiveData<List<TaskEntity>>


    @Insert
    suspend fun insertTask(task:TaskEntity)
}