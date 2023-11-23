package com.example.neobis_android_news_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.neobis_android_news_app.model.Article
import com.example.neobis_android_news_app.util.Converters


@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDatabase:RoomDatabase() {
    abstract fun articleDao():ArticleDao
    companion object{
        @Volatile
        private var INSTANCE: ArticleDatabase? = null
        fun getDatabase(context: Context):ArticleDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!= null){
                return tempInstance
            }
            synchronized(ArticleDatabase::class){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java,
                    "articleDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
