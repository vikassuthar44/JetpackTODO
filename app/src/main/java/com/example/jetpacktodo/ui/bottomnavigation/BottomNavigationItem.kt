package com.example.jetpacktodo.ui.bottomnavigation

import com.example.jetpacktodo.R

sealed class BottomNavigationItem(
    var title:String,var icon:Int, var screenRoute:String
) {
    object Home : BottomNavigationItem("Home", R.drawable.ic_home, "home")
    object Random : BottomNavigationItem("Random", R.drawable.ic_baseline_home_24, "random")
    object Task : BottomNavigationItem("Task", R.drawable.ic_add_task, "task")
    object Schedule : BottomNavigationItem("Schedule", R.drawable.ic_schedule, "schedule")
    object Profile : BottomNavigationItem("Profile", R.drawable.ic_profile, "profile")
}
