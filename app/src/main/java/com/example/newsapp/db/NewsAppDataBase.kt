package com.example.newsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.dataclass.Article
import com.example.newsapp.db.typeconverter.TypeConverter

@Database(entities = [Article::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class NewsAppDataBase : RoomDatabase() {

    abstract fun instanceOfNewsAppDaoInDB(): NewsAppDao

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

    //create an instance of the database
//
//    companion object{
//
//        //@Volatile to ensure visibility of this DB within threads, all threads know when
//        //the instance of this DB is created
//        @Volatile
//        private var instanceOfDB : NewsAppDataBase? = null
//        //This ensures only a single instance of our DB at once
//        private val LOCK = Any()
//
//        //Invoke function is called when ever you initialise the DB class
//        operator fun invoke(context: Context) = instanceOfDB ?: synchronized(LOCK){
//            //to ensure no other thread initialises the DB while we have already initialised it
//            instanceOfDB ?: createDB(context).also{
//                instanceOfDB = it
//            }
//        }
//
//        private fun createDB(context: Context) = Room.databaseBuilder(
//            context.applicationContext,
//            NewsAppDataBase::class.java,
//            "news_app.db"
//        ).build()
//    }
}