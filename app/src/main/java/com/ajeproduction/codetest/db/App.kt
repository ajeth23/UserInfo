package com.ajeproduction.codetest.db

import android.app.Application
import androidx.room.Room

class App : Application() {
    companion object {
        lateinit var database: Database
            private set
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            Database::class.java,
            "database" //name of the database
        ).build()
    }

}