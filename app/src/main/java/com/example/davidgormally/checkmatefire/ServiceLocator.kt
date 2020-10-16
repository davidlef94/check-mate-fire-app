package com.example.davidgormally.checkmatefire

import android.content.Context
import androidx.room.Room
import com.example.davidgormally.checkmatefire.database.AppDatabase
import com.example.davidgormally.checkmatefire.database.source.FireDoorLocalDataSource
import com.example.davidgormally.checkmatefire.database.source.MediaLocalDataSource
import com.example.davidgormally.checkmatefire.repository.Repository

object ServiceLocator {

    private var database: AppDatabase? = null

    @Volatile
    var repository: Repository? = null

    fun provideRepository(context: Context): Repository {
        synchronized(this) {
            return repository ?: createRepository(context)
        }
    }

    private fun createRepository(context: Context): Repository {
        val newRepository = Repository(createFireDoorLocalDataSource(context), createMediaLocalDataSource(context))
        repository = newRepository

        return newRepository
    }

    private fun createFireDoorLocalDataSource(context: Context): FireDoorLocalDataSource {
        val database = database ?: createDatabase(context)
        return FireDoorLocalDataSource(database.fireDoorDao())
    }

    private fun createMediaLocalDataSource(context: Context): MediaLocalDataSource {
        val database = database ?: createDatabase(context)
        return MediaLocalDataSource(database.mediaDao())
    }

    private fun createDatabase(context: Context): AppDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "App.db"
        )
            .fallbackToDestructiveMigration()
            .build()
        database = result

        return result
    }
}