package com.setiaki.moviecatalogue.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.setiaki.moviecatalogue.data.local.dao.*
import com.setiaki.moviecatalogue.data.local.entity.*


@Database(
    entities = [
        MovieEntity::class,
        TvShowEntity::class,
        GenreEntity::class,
        MovieGenreCrossRefEntity::class,
        TvShowGenreCrossRefEntity::class,
        MoviePageEntity::class,
        TvShowPageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
    abstract fun genreDao(): GenreDao
    abstract fun movieGenreCrossRefDao(): MovieGenreCrossRefDao
    abstract fun tvShowGenreCrossRefDao(): TvShowGenreCrossRefDao
    abstract fun moviePageDao() : MoviePageDao
    abstract fun tvShowPageDao() : TvShowPageDao

    companion object {
        private const val DATABASE_NAME = "app_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}