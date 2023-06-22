package com.example.sevenminutesworkout

import android.app.Application

class WorkoutApp: Application() {
    val db by lazy {
        HistoryDataBase.getInstance(this)
    }
}