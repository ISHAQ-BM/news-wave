package com.example.newswave.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newswave.R
import com.example.newswave.databinding.FragmentArticleBinding
import com.example.newswave.domain.models.Article
import com.example.newswave.presentation.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private var binding: FragmentArticleBinding? = null
    private val args: ArticleFragmentArgs by navArgs()
    private val viewModel: NewsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentArticleBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url= args.article.link
        binding?.webView?.webViewClient= WebViewClient()
        binding?.webView?.loadUrl(url)

        binding?.navigatePrev?.setOnClickListener {
            findNavController().popBackStack()
        }

        binding?.share?.setOnClickListener {
            shareArticle(args.article.link)
        }

        if (args.article.isBookmarked){
            binding?.bookmark?.setImageResource(R.drawable.ic_baseline_bookmark)
            binding?.bookmark?.setOnClickListener {
                viewModel.deleteArticle(args.article)
                binding?.bookmark?.setImageResource(R.drawable.ic_outline_bookmark)
            }
        }
        else{
            binding?.bookmark?.setImageResource(R.drawable.ic_outline_bookmark)
            binding?.bookmark?.setOnClickListener {
                viewModel.bookmarkArticle(args.article)
                binding?.bookmark?.setImageResource(R.drawable.ic_baseline_bookmark)
            }
        }





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