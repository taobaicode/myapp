package com.example.myapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Myapp : Application(
) {
    init {
        Timber.plant(Timber.DebugTree())
    }
}