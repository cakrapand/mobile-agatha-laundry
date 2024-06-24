package com.example.agathalaundry2.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.agathalaundry2.ui.history.HistoryFragment

class SectionPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {

        val fragment = HistoryFragment()
        fragment.arguments = Bundle().apply {
            putInt(HistoryFragment.ARG_SECTION_NUMBER, position)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }



}