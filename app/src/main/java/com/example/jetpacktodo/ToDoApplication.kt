package com.example.jetpacktodo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ToDoApplication: Application()  {

    override fun onCreate() {
        super.onCreate()
    }
}
