package com.example.faster

import android.Manifest
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private var bluetoothAdapter: BluetoothAdapter? = null
    private val LOCATION_REQ = 101
    private val BLUETOOTH_REQ = 102
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBluetooth()
    }

    private fun initBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null)
            Toast.makeText(this, "No bluetooth found.", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            (R.id.menu_search_devices) -> {
                Toast.makeText(this, "clicks search devices", Toast.LENGTH_SHORT).show()
                true;
            }
            (R.id.menu_enable_bluetooth) -> {
                enableBluetooth()
                true;
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkForPermission(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                "fine_location",
                LOCATION_REQ
            )
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

                else -> ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permission),
                    requestCode
                )
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
            LOCATION_REQ -> innerCheck("camera")
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


    private fun enableBluetooth() {
        if (bluetoothAdapter?.isEnabled() == true) {
            Toast.makeText(this, "Bluetooth is already enabled", Toast.LENGTH_SHORT).show()
        } else {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                checkForPermission(
                    android.Manifest.permission.BLUETOOTH,
                    "Enable Bluetooth",
                    BLUETOOTH_REQ
                )
            }
        }
    }
}
