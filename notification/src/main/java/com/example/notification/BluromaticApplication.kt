package com.example.notification

import android.app.Application
import com.example.notification.data.AppContainer
import com.example.notification.data.DefaultAppContainer

class BluromaticApplication : Application()  {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}