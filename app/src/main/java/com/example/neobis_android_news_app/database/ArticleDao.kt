package com.example.neobis_android_news_app.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.neobis_android_news_app.model.Article


@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)
    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>
    @Delete
    fun delete(article: Article)

}