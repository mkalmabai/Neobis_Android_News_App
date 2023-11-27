package com.example.neobis_android_news_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.neobis_android_news_app.database.ArticleRepository


class ViewModelProviderFactory(
    val articleRepository: ArticleRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(articleRepository) as T
    }

}
