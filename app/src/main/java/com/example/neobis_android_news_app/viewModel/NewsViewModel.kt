package com.example.neobis_android_news_app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.collections.filter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neobis_android_news_app.database.ArticleRepository
import com.example.neobis_android_news_app.model.Article
import com.example.neobis_android_news_app.model.NewsResponse
import com.example.neobis_android_news_app.util.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import retrofit2.Response
import retrofit2.http.Query
import java.util.Locale.IsoCountryCode

class NewsViewModel(val articleRepository: ArticleRepository):ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = articleRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        articleRepository.insert(article)
    }

    fun getSavedNews() = articleRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        articleRepository.deleteArticle(article)

    }
    fun searchNews(searchQuery: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = articleRepository.searchNews(searchQuery)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }


}


