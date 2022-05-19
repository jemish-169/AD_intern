package com.example.intents

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second)
        btnGoBack.setOnClickListener {
            finish()
        }
        btnGotoThird.setOnClickListener {
            Intent(this,third_activity::class.java).also {
                startActivity(it)
            }
        }

    }
}