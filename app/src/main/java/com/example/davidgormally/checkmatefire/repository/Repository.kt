package com.example.davidgormally.checkmatefire.repository

import androidx.lifecycle.LiveData
import com.example.davidgormally.checkmatefire.database.entity.FireDoor
import com.example.davidgormally.checkmatefire.database.entity.Media
import com.example.davidgormally.checkmatefire.database.source.FireDoorLocalDataSource
import com.example.davidgormally.checkmatefire.database.source.MediaLocalDataSource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class Repository (
    private val fireDoorLocalDataSource: FireDoorLocalDataSource,
    private val mediaLocalDataSource: MediaLocalDataSource
) {

    suspend fun insertFireDoor(fireDoor: FireDoor) {
        coroutineScope {
            launch { fireDoorLocalDataSource.insertFireDoor(fireDoor) }
        }
    }

    suspend fun updateFireDoor(fireDoor: FireDoor) {
        coroutineScope {
            launch { fireDoorLocalDataSource.updateFireDoor(fireDoor) }
        }
    }

    suspend fun deleteFireDoorById(id: String) {
        coroutineScope {
            launch { fireDoorLocalDataSource.deleteFireDoorById(id) }
        }
    }

    fun getAllFireDoors(): LiveData<List<FireDoor>> {
        return fireDoorLocalDataSource.getAllFireDoors()
    }

    suspend fun getFireDoorById(id: String): FireDoor {
        return fireDoorLocalDataSource.getFireDoorById(id)
    }

    /*
      Media dao interaction here
    */

    suspend fun insertMedia(media: Media) {
        coroutineScope {
            launch { mediaLocalDataSource.insertMedia(media) }
        }
    }

    suspend fun deleteMediaById(id: String) {
        coroutineScope {
            launch { mediaLocalDataSource.deleteMediaById(id) }
        }
    }

    suspend fun getMediaById(id: String): Media {
        return mediaLocalDataSource.getMediaById(id)
    }

    fun getAllMediaByFireDoorId(id: String): LiveData<List<Media>> {
        return mediaLocalDataSource.getAllMediaByFireDoorId(id)
    }
}