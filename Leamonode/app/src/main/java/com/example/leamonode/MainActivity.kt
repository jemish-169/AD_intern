package com.example.leamonode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    fun finish_juice() {
        tvText.text = "Congrulations🥳"
        ivImage.setImageResource(R.drawable.empty_glass)
        tvCount.text = ""
        btnSubmit.text = "Exit"
        btnSubmit.setOnClickListener() {
            finish()
        }
    }

    fun make_juice() {
        ivImage.setImageResource(R.drawable.leamon_juice)
        tvText.text = "Your juice is ready."
        tvCount.text = ""
        btnSubmit.text = "Finish it"
        ivImage.setOnClickListener() {}
        btnSubmit.setOnClickListener()
        {
            finish_juice()
        }
    }

    fun cut_lemon(count2: Int) {
        ivImage.setImageResource(R.drawable.leamon)
        tvText.text = "Cut lemons as you want for juice."
        tvCount.text = "Cutting lemons : 0"
        btnSubmit.text = "Go for making Juice"
        var count = 0
        ivImage.setOnClickListener()
        {
            count++
            tvCount.text = "Cutting lemons : $count/$count2"
            if (count == count2) {
                Toast.makeText(this, "Making juice of $count lemon", Toast.LENGTH_SHORT).show()
                make_juice()
            }

        }
        btnSubmit.setOnClickListener()
        {
            if (count == 0) {
                Toast.makeText(this, "Cut at least 1 lemon", Toast.LENGTH_SHORT).show()
                cut_lemon(count2)
            } else {
                Toast.makeText(this, "Making juice of $count lemon", Toast.LENGTH_SHORT).show()
                make_juice()
            }
        }
    }

    fun pick_leamon() {
        var count = 0
        tvText.text = "Click on tree to pick lemons."
        btnSubmit.text = "Go for cutting"
        ivImage.setOnClickListener()
        {
            count++
            tvCount.text = "Picked leamon : $count"
        }
        btnSubmit.setOnClickListener()
        {
            if (count == 0) {
                Toast.makeText(this, "Pick at least 1 lemon", Toast.LENGTH_SHORT).show()
                pick_leamon()
            } else {
                Toast.makeText(this, "You picked $count lemon", Toast.LENGTH_SHORT).show()
                cut_lemon(count)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pick_leamon()
    }
}