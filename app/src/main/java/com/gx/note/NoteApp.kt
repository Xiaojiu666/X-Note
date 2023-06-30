package com.gx.note

import android.app.Application
import androidx.room.Room
import com.gx.note.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApp : Application() {

    companion object {
        lateinit var instance: NoteApp
            private set
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
    }


}