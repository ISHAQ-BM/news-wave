package com.example.newswave.presentation.ui.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newswave.R
import com.example.newswave.databinding.FragmentHomeBinding
import com.example.newswave.databinding.FragmentSearchNewsBinding
import com.example.newswave.domain.models.Article
import com.example.newswave.domain.utils.Resource
import com.example.newswave.presentation.adapters.NewsAdapter
import com.example.newswave.presentation.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {

    private var binding: FragmentSearchNewsBinding? = null

    private val viewModel: NewsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        val adapter= NewsAdapter(
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

        binding?.recyclerView?.adapter = adapter

        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding?.searchView!!.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchNews(newText)
                return true
            }
        })

        viewModel.searchNews.observe(viewLifecycleOwner) {
            when(it){
                is Resource.Loading ->{
                    binding?.statusImage?.visibility = View.GONE
                    binding?.progressbar?.visibility=View.VISIBLE
                }
                is Resource.Success ->{
                    binding?.statusImage?.visibility = View.GONE
                    binding?.progressbar?.visibility=View.INVISIBLE
                    adapter.submitList(it.data?.articles)
                }
                is Resource.Error ->{
                    binding?.progressbar?.visibility=View.INVISIBLE
                    if (it.message == "Please check your network connection"){
                        binding?.statusImage?.visibility = View.VISIBLE
                    }

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


    private fun shareArticle(link: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun displayArticle(article: Article) {
        val action = SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(article)
        findNavController().navigate(action)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null

    }


}