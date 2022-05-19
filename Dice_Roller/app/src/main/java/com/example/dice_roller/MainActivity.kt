package com.example.dice_roller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnRoll.setOnClickListener {
            when((1..6).random())
            {
                1 -> ivImage1.setImageResource(R.drawable.dice_1)
                2 -> ivImage1.setImageResource(R.drawable.dice_2)
                3 -> ivImage1.setImageResource(R.drawable.dice_3)
                4 -> ivImage1.setImageResource(R.drawable.dice_4)
                5 -> ivImage1.setImageResource(R.drawable.dice_5)
                6 -> ivImage1.setImageResource(R.drawable.dice_6)
            }
            when((1..6).random())
            {
                1 -> ivImage2.setImageResource(R.drawable.dice_1)
                2 -> ivImage2.setImageResource(R.drawable.dice_2)
                3 -> ivImage2.setImageResource(R.drawable.dice_3)
                4 -> ivImage2.setImageResource(R.drawable.dice_4)
                5 -> ivImage2.setImageResource(R.drawable.dice_5)
                6 -> ivImage2.setImageResource(R.drawable.dice_6)
            }
            Toast.makeText(this,"Dice Rolled!",Toast.LENGTH_SHORT).show()
        }
    }
}