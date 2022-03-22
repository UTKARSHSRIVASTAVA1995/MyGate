package com.utkarsh.spokesly.cognito

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SpokeslyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}