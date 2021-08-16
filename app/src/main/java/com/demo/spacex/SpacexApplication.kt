package com.demo.spacex

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Process
import android.webkit.WebView
import androidx.multidex.MultiDexApplication

class SpacexApplication : MultiDexApplication() {

    private val TAG: String = SpacexApplication::class.java.simpleName

    companion object {
        // storing Android Application Context is safe
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}