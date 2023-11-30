package com.example.neobis_android_news_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.neobis_android_news_app.MainActivity
import com.example.neobis_android_news_app.R
import com.example.neobis_android_news_app.databinding.FragmentDetailBinding
import com.example.neobis_android_news_app.model.Article
import com.example.neobis_android_news_app.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: NewsViewModel
    val args: DetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val article = args.article

        Log.d("DetailFragment", "Received Article: ${args.article}")
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }

        binding.saveButton.setOnClickListener {
            binding.saveButton.setImageDrawable( ContextCompat.getDrawable(requireContext(), R.drawable.likefull))
            viewModel.saveArticle(article)

            }

    }
}