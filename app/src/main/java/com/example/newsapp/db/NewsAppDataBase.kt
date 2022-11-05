package com.example.newsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.ui.NewsResponse

//@Database(entities = [NewsResponseDto.Article::class], version = 1)
@Database(entities = [NewsResponse::class], version = 1)
//@TypeConverters(TypeConverter::class)
abstract class NewsAppDataBase : RoomDatabase() {

    abstract fun instanceOfAppDao(): NewsAppDao

    /**
     * Now since i'm using DI and its needed for every dependencies like
     * 1. Retrofit
     * 2. DB
     *
     * Note i will need to create an instance of database inside repository
     * and since this is what DI came to solve, i need to introduce DI to my database
     * so it creates the instances itself instead of me creating it here and using it in the
     * the repository this helps to reduce the below line commented code
     *
     * To tell dagger how to create an instance of the DB and how to inject it inside the
     * repository, you must create a manuel to tell dagger how to create it
     */

}