package com.example.mvvmsecond

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mvvmsecond.Fragments.CartFragment
import com.example.mvvmsecond.Fragments.MyOrderFragment
import com.example.mvvmsecond.Fragments.ProductFragment
import com.example.mvvmsecond.Fragments.ProfileFragment
import com.example.mvvmsecond.Fragments.UpLoadProductFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var buttonNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val showFragment=intent.getStringExtra("showFragment")

        if (showFragment!!.isNotEmpty()){
            loadFragment(CartFragment())
        }
        else{
            loadFragment(ProductFragment())
        }

        buttonNavigationView = findViewById(R.id.bottom_navigation)!!

        buttonNavigationView.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.menu_home -> {
                    loadFragment(ProductFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.menu_card -> {
                    loadFragment(CartFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.menu_my_Order -> {
                    loadFragment(MyOrderFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.menu_Upload -> {
                    loadFragment(UpLoadProductFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.menu_settings -> {
                    loadFragment(ProfileFragment())
                    return@setOnItemSelectedListener true
                }


                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }
}