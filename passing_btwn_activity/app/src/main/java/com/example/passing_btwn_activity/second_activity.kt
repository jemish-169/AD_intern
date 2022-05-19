package com.example.passing_btwn_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class second_activity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

//        val name = intent.getStringExtra("EXTRA_NAME")
//        val age = intent.getIntExtra("EXTRA_AGE",0)
//        val country = intent.getStringExtra("EXTRA_COUNTRY")
        val person = intent.getSerializableExtra("EXTRA_PERSON")
        tvResult.text = person.toString()

        btnGoback.setOnClickListener {
            finish()
        }
    }
}