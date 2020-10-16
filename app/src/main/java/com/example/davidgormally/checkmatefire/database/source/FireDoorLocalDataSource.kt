package com.example.davidgormally.checkmatefire.database.source

import androidx.lifecycle.LiveData
import com.example.davidgormally.checkmatefire.database.dao.FireDoorDao
import com.example.davidgormally.checkmatefire.database.entity.FireDoor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FireDoorLocalDataSource internal constructor(
    private val fireDoorDao: FireDoorDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
){

    suspend fun insertFireDoor(fireDoor: FireDoor) = withContext(ioDispatcher) {
        fireDoorDao.insertFireDoor(fireDoor)
    }

    suspend fun updateFireDoor(fireDoor: FireDoor) = withContext(ioDispatcher) {
        fireDoorDao.updateFireDoor(fireDoor)
    }

    suspend fun deleteFireDoorById(id: String) = withContext(ioDispatcher) {
        fireDoorDao.deleteFireDoorById(id)
    }

    fun getAllFireDoors(): LiveData<List<FireDoor>> {
        return fireDoorDao.getAllFireDoors()
    }

    suspend fun getFireDoorById(id: String): FireDoor = withContext(ioDispatcher) {
        return@withContext fireDoorDao.getFireDoorById(id)
    }
}