package com.example.permissions

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRequestPermission.setOnClickListener {
            requestPermission()
        }
    }
    private fun hasWriteExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED

    private fun hasLocationForergroundPermission() =
        ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED

    private fun hasLocationBackgroundPermission() =
        ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)== PackageManager.PERMISSION_GRANTED

    private fun requestPermission() {
        var permissionToRequest = mutableListOf<String>()
        if(hasWriteExternalStoragePermission())
        {
            permissionToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if(hasLocationForergroundPermission())
        {
            permissionToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if(hasLocationBackgroundPermission())
        {
            permissionToRequest.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        if(permissionToRequest.isNotEmpty())
        {
            ActivityCompat.requestPermissions(this,permissionToRequest.toTypedArray(),0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0 && grantResults.isNotEmpty()){
            for(i in grantResults.indices)
            {
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.d("PermissionsRequest","${permissions[i]} Granted.")
                }
            }
        }
    }
}