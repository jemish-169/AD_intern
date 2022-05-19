package com.example.tablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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

        TabLayoutMediator(tabLayout,viewPager){
            tab,position ->
            tab.text="Tab ${position+1}"
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener
        {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Toast.makeText(this@MainActivity,"selected ${tab?.text}",Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Toast.makeText(this@MainActivity,"Unselected ${tab?.text}",Toast.LENGTH_SHORT).show()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Toast.makeText(this@MainActivity,"Reselected",Toast.LENGTH_SHORT).show()
            }
        })
    }
}