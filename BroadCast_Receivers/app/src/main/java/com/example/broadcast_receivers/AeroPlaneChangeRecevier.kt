package com.example.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AeroPlaneChangeRecevier: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isAeroPlannedEnable = intent?.getBooleanExtra("state",false) ?: return
        if(isAeroPlannedEnable){
           Toast.makeText(context,"Aeroplane mode Enabled.",Toast.LENGTH_SHORT).show()
        }
        else
           Toast.makeText(context,"Aeroplane mode Disabled.",Toast.LENGTH_SHORT).show()

    }
}