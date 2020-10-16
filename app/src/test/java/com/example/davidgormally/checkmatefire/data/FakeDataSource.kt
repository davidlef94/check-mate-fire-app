package com.example.davidgormally.checkmatefire.data

import com.example.davidgormally.checkmatefire.database.entity.FireDoor

class FakeDataSource(var fireDoors: MutableList<FireDoor>? = mutableListOf()) {

    suspend fun getAllFireDoors(): List<FireDoor> {
        fireDoors?.let { return ArrayList(it) }
        return emptyList()
    }

    suspend fun insertFireDoor(fireDoor: FireDoor) {
        fireDoors?.add(fireDoor)
    }
}