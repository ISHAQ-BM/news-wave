package com.example.newswave.presentation.ui.fragments

import android.content.Intent
import android.os.Build

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newswave.R
import com.example.newswave.databinding.FragmentViewPagerBinding

import com.example.newswave.domain.models.Article
import com.example.newswave.core.Resource
import com.example.newswave.presentation.adapters.NewsAdapter
import com.example.newswave.presentation.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    private var binding: FragmentViewPagerBinding? = null


    private val viewModel: NewsViewModel by activityViewModels()

    var isLoading=false
    var isScrolling=false
    var isLastPage=false
    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager=recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition=layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount=layoutManager.childCount
            val totalItemCount=layoutManager.itemCount

            val isNotLoadingAndNotLastPage= !isLoading && !isLastPage
            val isAtLastItem =firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning=firstVisibleItemPosition >=0

            val shouldPaging= isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isScrolling
            if (shouldPaging){
                viewModel.loadMoreNews(viewModel.latestNews.value!!.data!!.articles[0].category)
                isScrolling=false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                isScrolling=true
        }

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding?.root
       }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        val adapter=NewsAdapter(
            {article -> displayArticle(article) },
            object : NewsAdapter.OptionsMenuClickListener{
                // implement the required method
                @RequiresApi(Build.VERSION_CODES.Q)
                override fun onOptionsMenuClicked(article: Article, view: View) {
                    // this method will handle the onclick options click
                    // it is defined below
                    performOptionsMenuClick(article,view)
                }
            }
        )

        binding?.recyclerView?.adapter=adapter
        binding?.recyclerView?.addOnScrollListener(scrollListener)

        viewModel.latestNews.observe(viewLifecycleOwner) {
            when(it){
                is Resource.Loading -> {
                    adapter.submitList(it.data?.articles)
                    binding?.statusImage?.visibility =View.INVISIBLE
                    binding?.progressbar?.visibility = View.VISIBLE

                }
                is Resource.Success ->  {
                    binding?.statusImage?.visibility =View.INVISIBLE
                    binding?.progressbar?.visibility=View.INVISIBLE
                    adapter.submitList(it.data?.articles)
                }
                is Resource.Error -> {
                    adapter.submitList(it.data?.articles)
                    binding?.progressbar?.visibility = View.INVISIBLE
                    binding?.statusImage?.visibility =View.VISIBLE
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun performOptionsMenuClick(article: Article, view: View) {
        val popupMenu = PopupMenu(requireContext() , view)
        if (article.isBookmarked) {
            popupMenu.inflate(R.menu.popup_menu_baseline_bookmark)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.share -> {
                        shareArticle(article.link)
                        true
                    }

                    R.id.bookmark -> {
                        viewModel.deleteArticle(article)
                        Toast.makeText(requireContext(), "article unsaved", Toast.LENGTH_LONG)
                            .show()
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
        } else {
            popupMenu.inflate(R.menu.popup_menu_outline_bookmark)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.share -> {
                        shareArticle(article.link)
                        true
                    }

                    R.id.bookmark -> {
                        viewModel.bookmarkArticle(article)
                        Toast.makeText(requireContext(), "article saved", Toast.LENGTH_LONG).show()
                        true
                    }

                    else -> {
                        false
                    }
                }

            }
        }
        popupMenu.setForceShowIcon(true)
        popupMenu.show()

    }

    private fun displayArticle(article: Article) {
        val action=HomeFragmentDirections.actionHomeFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }

    private fun shareArticle(link: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null

    }


}