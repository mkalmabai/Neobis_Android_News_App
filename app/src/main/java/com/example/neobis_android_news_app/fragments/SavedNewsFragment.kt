package com.example.neobis_android_news_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.query
import com.example.neobis_android_news_app.MainActivity
import com.example.neobis_android_news_app.R
import com.example.neobis_android_news_app.adapter.RecyclerViewAdapter
import com.example.neobis_android_news_app.databinding.FragmentSavedNewsBinding
import com.example.neobis_android_news_app.model.Article
import com.example.neobis_android_news_app.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment : Fragment() {
    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: RecyclerViewAdapter
    private lateinit var articleList: List<Article>
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
        search()
        initDeleteArticle()
        viewModel.getSavedNews()?.observe(viewLifecycleOwner, Observer { articles ->
            articleList = articles
            newsAdapter.differ.submitList(articles)
        })
    }

    private fun  search(){
        binding.savedSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    newsAdapter.differ.submitList(articleList)
                    newsAdapter.notifyDataSetChanged()
                } else {
                    newText?.let {
                        filterArticle(it)
                    }
                }
                return true
            }
        })


    }
    private fun filterArticle(query: String){
        val filteredList = articleList.filter {
            it.title!!.contains(query, ignoreCase = true) }
        newsAdapter.differ.submitList(filteredList)
        newsAdapter.notifyDataSetChanged()
    }
    private fun setupRecyclerview(){
        newsAdapter = RecyclerViewAdapter()
        binding.savedrecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        newsAdapter.setOnItemClickListener { article->
            article?.let {

            val action = SavedNewsFragmentDirections.actionSavedNewsFragmentToDetailFragment(article = it)
            findNavController().navigate(action)
            }
        }
    }

    private fun initDeleteArticle() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                view?.let {
                    Snackbar.make(it, getString(R.string.deleted) , Snackbar.LENGTH_SHORT).apply {
                        setAction(getString(R.string.undo)) {
                            viewModel.saveArticle(article)
                        }
                        show()
                    }
                }
            }
            }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.savedrecyclerView)
        }

        }

    }