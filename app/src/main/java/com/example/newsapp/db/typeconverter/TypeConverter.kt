package com.example.newsapp.db.typeconverter

import androidx.room.TypeConverter
import com.example.newsapp.data.remote.NewsDataClass
import org.json.JSONObject

class TypeConverter {

    //store source data class to DB with the help of Type converters
    @TypeConverter
    fun convertSourceToJSONObject(source: NewsDataClass.Article.Source) : String{
        return JSONObject().apply {
            put("ID", source.id)
            put("NAME", source.name)
        }.toString()
    }

    //retrieve source data in DB, since its been converted its now a type of string, so receive
    //a string then convert it to source so it could be used
    @TypeConverter
    fun convertJSONObjectBackToSource(string: String) : NewsDataClass.Article.Source {
        val json = JSONObject(string)
        return NewsDataClass.Article.Source(
            json.optString("ID"),
            json.optString("NAME")
        )
    }
}