package com.example.broadcast_receivers

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    lateinit var receiver : AeroPlaneChangeRecevier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

           receiver =  AeroPlaneChangeRecevier()

        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(AeroPlaneChangeRecevier(),it)

        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}