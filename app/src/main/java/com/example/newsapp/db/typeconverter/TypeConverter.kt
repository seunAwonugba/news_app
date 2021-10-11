package com.example.newsapp.db.typeconverter

import androidx.room.TypeConverter
import com.example.newsapp.dataclass.Source
import org.json.JSONObject

class TypeConverter {

    //store source data class to DB with the help of Type converters
    @TypeConverter
    fun convertSourceToJSONObject(source:Source) : String{
        return JSONObject().apply {
            put("ID", source.id)
            put("NAME", source.name)
        }.toString()
    }

    //retrieve source data in DB, since its been converted its now a type of string, so receive
    //a string then convert it to source so it could be used
    @TypeConverter
    fun convertJSONObjectBackToSource(string: String) : Source{
        val json = JSONObject(string)
        return Source(json.getString("ID"), json.getString("NAME"))
    }
}