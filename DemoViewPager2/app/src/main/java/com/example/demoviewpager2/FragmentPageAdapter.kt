package com.example.demoviewpager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentPageAdapter : FragmentStateAdapter {

    constructor(activity: FragmentActivity) : super(activity)

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        if(position % 2 == 0) {
            return FirstFragment()
        } else {
            return SecondFragment()
        }
    }
}