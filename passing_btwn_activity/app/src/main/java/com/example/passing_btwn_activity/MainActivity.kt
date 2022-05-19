package com.example.passing_btwn_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnapply.setOnClickListener {
            val name = etName.text.toString()
            val age = etage.text.toString().toInt()
            val country = etcountry.text.toString()
            val person = person_info(name,age,country)
            Intent(this,second_activity::class.java).also {
//            it.putExtra("EXTRA_NAME",name)
//            it.putExtra("EXTRA_AGE",age)
//            it.putExtra("EXTRA_COUNTRY",country)
                it.putExtra("EXTRA_PERSON",person)
                startActivity(it)
            }
        }
    }
}