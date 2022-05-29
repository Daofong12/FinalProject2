package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    var tabTitle = arrayOf("Home","Record","Message")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var pager = findViewById<ViewPager2>(R.id.viewPager2)
        var tl = findViewById<TabLayout>(R.id.tab)
        pager.adapter=PageAdapter(supportFragmentManager,lifecycle)

        TabLayoutMediator(tl,pager){
            tab,position->tab.text=tabTitle[position]
        }.attach()
    }
}