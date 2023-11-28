package com.example.neobis_android_news_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neobis_android_news_app.MainActivity
import com.example.neobis_android_news_app.adapter.RecyclerViewAdapter
import com.example.neobis_android_news_app.databinding.FragmentSavedNewsBinding
import com.example.neobis_android_news_app.viewModel.NewsViewModel

class SavedNewsFragment : Fragment() {
    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: RecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedNewsBinding.inflate(inflater,container,false)
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerview()

        viewModel.getSavedNews()?.observe(viewLifecycleOwner, Observer {
                articles -> newsAdapter.differ.submitList(articles)
        })
        viewModel.searchResults.observe(viewLifecycleOwner) { searchResults ->

            newsAdapter.differ.submitList(searchResults)
        }
        binding.newsSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.savedsearchNews(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text change if needed
                return true
            }
        })

    }
    private fun setupRecyclerview(){
        newsAdapter = RecyclerViewAdapter()
        binding.savedrecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        newsAdapter.setOnItemClickListener {
            val action = SavedNewsFragmentDirections.actionSavedNewsFragmentToDetailFragment(article = it)
            findNavController().navigate(action)
        }
    }
}