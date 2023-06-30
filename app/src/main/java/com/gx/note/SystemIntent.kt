package com.gx.note

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.AlarmClock
import android.provider.CalendarContract


//https://developer.android.google.cn/guide/components/intents-common?hl=zh-cn#AdbIntents

@SuppressLint("QueryPermissionsNeeded")
fun createAlarm(
    context: Context,
    packageManager: PackageManager,
    message: String,
    hour: Int,
    minutes: Int
) {
    val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
        putExtra(AlarmClock.EXTRA_MESSAGE, message)
        putExtra(AlarmClock.EXTRA_HOUR, hour)
        putExtra(AlarmClock.EXTRA_MINUTES, minutes)
    }
    if (intent.resolveActivity(packageManager) != null) {
        context.startActivity(intent)
    }
}


@SuppressLint("QueryPermissionsNeeded")
fun addCandlerEvent(
    context: Context,
    packageManager: PackageManager, title: String, location: String, begin: Long, end: Long
) {
    val intent = Intent(Intent.ACTION_INSERT).apply {
        data = CalendarContract.Events.CONTENT_URI
        putExtra(CalendarContract.Events.TITLE, title)
        putExtra(CalendarContract.Events.EVENT_LOCATION, location)
        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)
    }
    if (intent.resolveActivity(packageManager) != null) {
        context.startActivity(intent)
    }
}