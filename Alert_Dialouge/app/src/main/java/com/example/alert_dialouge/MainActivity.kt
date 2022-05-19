package com.example.alert_dialouge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addContactDialouge = AlertDialog.Builder(this)
            .setTitle("Add Contact")
            .setMessage("Do you want to Mr. Elite to your contact list?")
            .setIcon(R.drawable.ic_add_contact)
            .setPositiveButton("YES") { _, _ ->
                Toast.makeText(
                    this, "You Added Mr Elite in to your contact list", Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("NO") { _, _ ->
                Toast.makeText(
                    this,
                    "You didn't Add Mr Elite in to your contact list",
                    Toast.LENGTH_SHORT
                ).show()
            }.create()

        btndialouge1.setOnClickListener {
            addContactDialouge.show()
        }
        val options = arrayOf("first item", "second item", "third item")
        val singleChoiceDialog = AlertDialog.Builder(this)
            .setTitle("Choose one of this options ")
            .setSingleChoiceItems(options, 0) { _, i ->
                Toast.makeText(this, "You clicked on ${options[i]}", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("YES") { _, _ ->
                Toast.makeText(
                    this, "Accepted", Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("NO") { _, _ ->
                Toast.makeText(this, "Declined", Toast.LENGTH_SHORT).show()
            }.create()
        btnDialouge2.setOnClickListener {
            singleChoiceDialog.show()
        }
        val multiChoiceDialog = AlertDialog.Builder(this)
            .setTitle("Choose multi options")
            .setMultiChoiceItems(options, booleanArrayOf(false, false, false)) { _, i, checked ->
                if (checked) {
                    Toast.makeText(this, "You checked ${options[i]}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "You checked ${options[i]}", Toast.LENGTH_SHORT).show()
                }
            }
            .setPositiveButton("YES") { _, _ ->
                Toast.makeText(
                    this, "Accepted", Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("NO") { _, _ ->
                Toast.makeText(this, "Declined", Toast.LENGTH_SHORT).show()
            }.create()
        btnDialouge3.setOnClickListener {
            multiChoiceDialog.show()
        }
    }
}

