package com.example.newswave.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newswave.R
import com.example.newswave.databinding.FragmentBookmarkedNewsBinding
import com.example.newswave.databinding.FragmentHomeBinding
import com.example.newswave.domain.models.Article
import com.example.newswave.presentation.adapters.NewsAdapter
import com.example.newswave.presentation.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class BookmarkedNewsFragment : Fragment() {

    private var binding: FragmentBookmarkedNewsBinding? = null

    private val viewModel: NewsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookmarkedNewsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.savedRv?.layoutManager= LinearLayoutManager(requireContext())
        val adapter=NewsAdapter(
            {article -> displayArticle(article.link) },
            object : NewsAdapter.OptionsMenuClickListener{
                // implement the required method
                override fun onOptionsMenuClicked(article: Article, view: View) {
                    // this method will handle the onclick options click
                    // it is defined below
                    performOptionsMenuClick(article,view)
                }
            }
        )
        binding?.savedRv?.adapter=adapter
        viewModel.getBookmarkedArticles().observe(viewLifecycleOwner) {adapter.submitList(it)}

    }


    private fun performOptionsMenuClick(article: Article, view: View) {
        val popUpMenu = PopupMenu(requireContext(),view)

        popUpMenu.inflate(R.menu.popup_menu_baseline_bookmark)
        popUpMenu.setOnMenuItemClickListener {item ->
            when(item.itemId){
                R.id.share ->{
                    shareArticle(article.link)
                    true
                }
                R.id.bookmark ->{
                    viewModel.deleteArticle(article)
                    true
                }

                else -> {
                    false
                }
            }
        }



        popUpMenu.setForceShowIcon(true)
        popUpMenu.show()
    }

    private fun displayArticle(link: String) {
        val action=BookmarkedNewsFragmentDirections.actionBookmarkedNewsFragmentToArticleFragment(link)
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