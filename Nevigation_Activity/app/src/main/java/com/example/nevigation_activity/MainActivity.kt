package com.example.nevigation_activity

import SecondFrament
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment = FirstFrament()
        val secondFragment = SecondFrament()
        val thirdFragment = ThirdFrament()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.miHome -> setCurrentFragment(firstFragment)
                R.id.miMessages -> setCurrentFragment(secondFragment)
                R.id.miProfile -> setCurrentFragment(thirdFragment)
            }
            true
        }
        bottomNavigationView.getOrCreateBadge(R.id.miMessages).apply {
            number =10
            isVisible = true
        }
    }
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}