package com.example.finalproject

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PageAdapter(fm:FragmentManager,lifecycle:Lifecycle):FragmentStateAdapter(fm,lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0->return Home()
            1->return nav()
            2->return Message()
            else->return Home()
        }
    }

}