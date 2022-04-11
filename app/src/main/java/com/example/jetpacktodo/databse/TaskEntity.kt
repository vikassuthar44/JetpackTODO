package com.example.jetpacktodo.databse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "create_task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int = 0,
    @ColumnInfo(name = "taskTitle")
    var taskTitle:String,
    @ColumnInfo(name = "task_start_date")
    var taskStartDate:String,
    @ColumnInfo(name = "task_end_date")
    var taskEndDate:String,
    @ColumnInfo(name = "task_start_time")
    var taskStartTime:String,
    @ColumnInfo(name = "task_end_time")
    var taskEndTime:String,
    @ColumnInfo(name = "task_description")
    var taskDescription:String,
    @ColumnInfo(name = "task_category")
    var taskCategory:String
)
