package com.example.jetpacktodo.ui.task

import com.example.jetpacktodo.R

object TaskDataSource {

    fun getTaskData() : List<Task>  {
        return ArrayList<Task>().apply {
            add(Task("App Develop","10 Task",R.drawable.development, R.color.reminder, R.color.reminder_background))
            add(Task("New Tech","6 Task",R.drawable.tech, R.color.notification, R.color.notification_background))
            add(Task("Learning","8 Task",R.drawable.study, R.color.help_and_support, R.color.help_and_support_background))
            add(Task("Others","12 Task",R.drawable.other, R.color.event,R.color.event_background))
        }
    }

    fun getOnGoingTask():List<OnGoingTask> {
        return ArrayList<OnGoingTask>().apply {
            add(OnGoingTask("Startup Mobile","10:00 AM", "12:30 PM", R.drawable.development,"80%"))
            add(OnGoingTask("Startup Mobile App \nDesing with responsive","10:00 AM", "12:30 PM", R.drawable.tech,"80%"))
            add(OnGoingTask("Startup Mobile App \nDesing with responsive","10:00 AM", "12:30 PM", R.drawable.study,"80%"))
            add(OnGoingTask("Startup Mobile App \nDesing with responsive","10:00 AM", "12:30 PM", R.drawable.other,"80%"))
            add(OnGoingTask("Startup Mobile App \nDesing with responsive","10:00 AM", "12:30 PM", R.drawable.other,"80%"))
            add(OnGoingTask("Startup Mobile App \nDesing with responsive","10:00 AM", "12:30 PM", R.drawable.other,"80%"))
            add(OnGoingTask("Startup Mobile App \nDesing with responsive","10:00 AM", "12:30 PM", R.drawable.other,"80%"))
            add(OnGoingTask("Startup Mobile App \nDesing with responsive","10:00 AM", "12:30 PM", R.drawable.other,"80%"))
        }
    }
}