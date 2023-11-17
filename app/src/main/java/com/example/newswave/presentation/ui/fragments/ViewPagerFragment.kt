package com.example.newswave.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newswave.R
import com.example.newswave.databinding.FragmentViewPagerBinding
import com.example.newswave.presentation.adapters.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    private var binding:FragmentViewPagerBinding ? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding?.root
       }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle=arguments
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        val adapter=NewsAdapter()
        adapter.setOnItemClickListener {  }
        binding?.recyclerView?.adapter=adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null

    }


}