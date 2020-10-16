package com.example.davidgormally.checkmatefire.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.davidgormally.checkmatefire.database.entity.Media

@Dao
interface MediaDao {

    @Insert
    suspend fun insertMedia(media: Media)

    @Query("DELETE FROM media WHERE id = :mediaId")
    suspend fun deleteMediaById(mediaId: String)

    @Query("SELECT * FROM media WHERE id = :mediaId")
    suspend fun getMediaById(mediaId: String): Media

    @Query("SELECT * FROM media WHERE owner_id = :fireDoorId")
    fun getAllMediaByFireDoorId(fireDoorId: String): LiveData<List<Media>>
}