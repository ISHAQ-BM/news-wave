package com.example.newswave.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.newswave.databinding.FragmentHomeBinding
import com.example.newswave.core.categories
import com.example.newswave.presentation.adapters.PagerAdapter
import com.example.newswave.presentation.viewmodels.NewsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

    private val viewModel: NewsViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        val pagerAdapter=PagerAdapter(requireActivity())
        binding?.viewPager?.adapter = pagerAdapter

        binding?.tabLayout?.let {
            binding?.viewPager?.let { it1 ->
                TabLayoutMediator(it, it1){ tab, position ->
                    tab.text= categories[position]
                }.attach()
            }
        }


        binding?.tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.readNetworkState(tab?.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Toast.makeText(requireContext(),"",Toast.LENGTH_SHORT).show()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Toast.makeText(requireContext(),"",Toast.LENGTH_SHORT).show()
            }
        })





    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null

    }


}