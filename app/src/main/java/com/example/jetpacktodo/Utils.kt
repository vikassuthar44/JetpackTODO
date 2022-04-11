package com.example.jetpacktodo

object Utils {

    fun getMonthName(month:Int): String {
        val monthNames = arrayOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )
        return monthNames[month]
    }


    fun getTime(hours:Int, minute:Int):String {
        var newHour = hours
        var time = "AM"
        if(newHour >= 12) {
            newHour -= 12
            time = "PM"
        }

        return "$newHour:$minute $time"
    }
}