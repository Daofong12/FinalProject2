package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.*

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
    fun realizeNavigation(view:View){
        val intent = Intent(this,NavigationActivity::class.java).apply{}
        startActivity(intent)
    }
    fun recordStatusActivity(view: View){
        val intent = Intent(this,record_status::class.java).apply{}
        startActivity(intent)
    }
    fun recordNoteActivity(view: View){
        val intent = Intent(this,record_note::class.java).apply{}
        startActivity(intent)
    }
    fun recordChecklistActivity(view: View){
        val intent = Intent(this,record_checklist::class.java).apply{}
        startActivity(intent)
    }
}