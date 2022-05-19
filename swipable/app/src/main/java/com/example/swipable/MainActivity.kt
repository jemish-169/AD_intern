package com.example.swipable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val images = listOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image4,
            R.drawable.image5
        )
        val adapter = ViewPageAdapter(images)
        viewPager.adapter =adapter
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager.beginFakeDrag()
        viewPager.fakeDragBy(-10f)
        viewPager.endFakeDrag()
    }
}