package com.ygs.aimovies

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AIMovieApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}