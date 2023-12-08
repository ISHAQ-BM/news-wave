package com.example.newswave.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.newswave.R
import com.example.newswave.databinding.FragmentHomeBinding
import com.example.newswave.presentation.adapters.PagerAdapter
import com.example.newswave.presentation.viewmodels.NewsViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

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






        viewModel.latestNews.observe(viewLifecycleOwner) { it ->
            val pagerAdapter=PagerAdapter(this.requireActivity(), it.data?.results?.map { it.category[0] }?.toSet() ?: setOf())
            binding?.viewPager?.adapter = pagerAdapter
            binding?.tabLayout?.let {
                binding?.viewPager?.let { it1 ->
                    TabLayoutMediator(it, it1) { tab, position ->
                        tab.text = viewModel.latestNews.value?.data?.results?.map { it.category[0] }?.toSet()?.toList()
                            ?.get(position)
                    }.attach()
                }

            }
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null

    }


}