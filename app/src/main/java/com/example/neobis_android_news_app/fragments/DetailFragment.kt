package com.example.neobis_android_news_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.neobis_android_news_app.MainActivity
import com.example.neobis_android_news_app.databinding.FragmentDetailBinding
import com.example.neobis_android_news_app.viewModel.NewsViewModel


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: NewsViewModel
    private val args by navArgs<DetailFragmentArgs>()
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
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(args.article.url)
        }
    }
}