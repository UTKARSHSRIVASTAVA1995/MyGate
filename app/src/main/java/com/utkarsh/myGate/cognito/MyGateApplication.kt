package com.utkarsh.myGate.cognito

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyGateApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}