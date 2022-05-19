package com.example.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

abstract class MyService: Service() {
val Tag = "MyServices"
    init {
        Log.d(Tag,"Service is running...")
    }
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val dataString = intent?.getStringExtra("EXTRA_DATA")
        dataString?.let {
            Log.d(Tag,dataString)
        }
        return START_STICKY
    }
}