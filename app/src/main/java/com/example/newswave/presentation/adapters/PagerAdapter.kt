package com.example.newswave.presentation.adapters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newswave.domain.utils.categories
import com.example.newswave.presentation.ui.fragments.ViewPagerFragment

class PagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount()= categories.size

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = ViewPagerFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putString("category", categories[position])

        }
        return fragment
    }

}