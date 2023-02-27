package com.example.appt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApptApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
