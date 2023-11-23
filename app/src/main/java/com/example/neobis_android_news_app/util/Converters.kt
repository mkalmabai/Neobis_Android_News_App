package com.example.neobis_android_news_app.util

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.neobis_android_news_app.model.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source): String{
    return source.name
    }
    @TypeConverter
    fun toSource(name: String): Source{
        return Source(name,name)
    }
}