package com.example.davidgormally.checkmatefire.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.davidgormally.checkmatefire.database.dao.FireDoorDao
import com.example.davidgormally.checkmatefire.database.dao.MediaDao
import com.example.davidgormally.checkmatefire.database.entity.FireDoor
import com.example.davidgormally.checkmatefire.database.entity.Media

@Database(entities = [FireDoor::class, Media::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fireDoorDao(): FireDoorDao
    abstract fun mediaDao(): MediaDao
}