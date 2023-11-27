package com.example.neobis_android_news_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neobis_android_news_app.MainActivity
import com.example.neobis_android_news_app.R
import com.example.neobis_android_news_app.adapter.RecyclerViewAdapter
import com.example.neobis_android_news_app.databinding.FragmentNewsBinding
import com.example.neobis_android_news_app.model.Article
import com.example.neobis_android_news_app.util.Resource
import com.example.neobis_android_news_app.viewModel.NewsViewModel


class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
     lateinit var viewModel: NewsViewModel
     lateinit var newsAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater,container,false)


        binding.update.setOnClickListener {
            newsAdapter.differ.submitList(listOf())
            viewModel.getBreakingNews("us")
        }
        binding.favourite.setOnClickListener {
            findNavController().navigate(R.id.action_newsFragment_to_savedNewsFragment)
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setupRecyclerview()

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let{newsResponse ->
                         newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e("BreakingNewsFragment", "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }
    private fun hideProgressBar(){
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerview(){
        newsAdapter = RecyclerViewAdapter()
        binding.newsRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        newsAdapter.setOnItemClickListener {
            val action = NewsFragmentDirections.actionNewsFragmentToDetailFragment(article = it)
            findNavController().navigate(action)
        }
    }



}