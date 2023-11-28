package com.example.neobis_android_news_app.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neobis_android_news_app.api.ApiInterface
import com.example.neobis_android_news_app.api.RetrofitInstance
import com.example.neobis_android_news_app.model.Article
import com.example.neobis_android_news_app.model.NewsResponse
import com.example.neobis_android_news_app.util.Resource
import retrofit2.Retrofit
import retrofit2.http.Query
import java.util.Locale.IsoCountryCode

class ArticleRepository(
    private val db: ArticleDatabase
    ) {

   suspend fun getBreakingNews(countryCode:String,pageNumber: Int) =
    RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun insert(article: Article) = db.getArticleDao().insert(article)

      fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().delete(article)
    suspend fun searchNews (query: String) = RetrofitInstance.api.searchForNews(query)

}