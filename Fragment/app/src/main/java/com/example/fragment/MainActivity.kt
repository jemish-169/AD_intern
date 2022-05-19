package com.example.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstfragment = firstFragment()
        val secondfragment = secondFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flfrgment, firstfragment)
        }
        btnFragment1.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flfrgment, firstfragment)
                addToBackStack(null)
                commit()

            }
        }
        btnFragment2.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flfrgment, secondfragment)
                addToBackStack(null)
                commit()
            }
        }
    }
}