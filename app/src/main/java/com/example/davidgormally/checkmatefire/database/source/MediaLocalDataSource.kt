package com.example.davidgormally.checkmatefire.database.source

import androidx.lifecycle.LiveData
import com.example.davidgormally.checkmatefire.database.dao.MediaDao
import com.example.davidgormally.checkmatefire.database.entity.Media
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaLocalDataSource internal constructor(
    private val mediaDao: MediaDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
){
    suspend fun insertMedia(media: Media) = withContext(ioDispatcher) {
        mediaDao.insertMedia(media)
    }

    suspend fun deleteMediaById(id: String) = withContext(ioDispatcher) {
        mediaDao.deleteMediaById(id)
    }

    suspend fun getMediaById(id: String): Media = withContext(ioDispatcher) {
        return@withContext mediaDao.getMediaById(id)
    }

    fun getAllMediaByFireDoorId(id: String): LiveData<List<Media>> {
        return mediaDao.getAllMediaByFireDoorId(id)
    }
}