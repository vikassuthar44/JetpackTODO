package com.example.jetpacktodo.ui.profile

import com.example.jetpacktodo.R

object ProfileSettingDataSource {

    fun profileSettingDatas() :List<ProfileSettingData> {
        return ArrayList<ProfileSettingData>().apply {
            add(ProfileSettingData("Reminders",R.drawable.ic_clock,true,R.color.reminder,R.color.reminder_background))
            add(ProfileSettingData("Notifications",R.drawable.ic_notifications,true,R.color.notification,R.color.notification_background))
            add(ProfileSettingData("Help & Support",R.drawable.ic_helpsupport,true,R.color.help_and_support,R.color.help_and_support_background))
            add(ProfileSettingData("Event Remind",R.drawable.ic_event,false,R.color.event,R.color.event_background))
        }
    }
}