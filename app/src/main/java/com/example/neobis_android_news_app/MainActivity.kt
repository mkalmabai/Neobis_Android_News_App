package com.example.neobis_android_news_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.neobis_android_news_app.database.ArticleDao
import com.example.neobis_android_news_app.database.ArticleDatabase
import com.example.neobis_android_news_app.database.ArticleRepository
import com.example.neobis_android_news_app.databinding.ActivityMainBinding
import com.example.neobis_android_news_app.viewModel.NewsViewModel
import com.example.neobis_android_news_app.viewModel.ViewModelProviderFactory
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = ArticleRepository (ArticleDatabase(this))
        val viewModelProviderFactory = ViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
    }
}