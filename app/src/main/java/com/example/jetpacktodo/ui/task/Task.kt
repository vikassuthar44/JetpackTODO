package com.example.jetpacktodo.ui.task

data class Task(
    val title:String,
    val noOfTask:String,
    val taskIcon:Int,
    val taskTitleColor:Int,
    val taskColor:Int
)


data class OnGoingTask(
    val tasktitle:String,
    val taskTimeStart:String,
    val taskEndTime:String,
    val taskIcons:Int,
    val taskCompletion:String
)