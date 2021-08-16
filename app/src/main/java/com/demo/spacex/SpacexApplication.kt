package com.demo.spacex

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication

class SpacexApplication: MultiDexApplication() {

    private val TAG: String = SpacexApplication::class.java.simpleName

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}