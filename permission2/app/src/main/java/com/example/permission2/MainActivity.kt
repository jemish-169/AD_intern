package com.example.permission2

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val caamera_rq = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button()
    }

    private fun button() {
        btnCamera.setOnClickListener {
            checkForPermission(android.Manifest.permission.CAMERA, "Camera", caamera_rq)
        }
    }

    private fun checkForPermission(permission: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {

                ContextCompat.checkSelfPermission(
                    applicationContext,
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT)
                        .show()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialogue(
                    permission,
                    name,
                    requestCode
                )

                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "$name refused", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "$name granted", Toast.LENGTH_SHORT).show()

            }
        }
        when (requestCode) {
            caamera_rq -> innerCheck("camera")
        }
    }

    private fun showDialogue(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Permission to access your $name is require to use this app")
            setTitle("Permission Required")
            setPositiveButton("OK") { _, _ ->
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(permission),
                    requestCode
                )
            }
        }
        val dialog = builder.create()
        dialog.show()
    }
}