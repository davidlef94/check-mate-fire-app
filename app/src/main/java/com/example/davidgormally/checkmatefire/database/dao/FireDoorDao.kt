package com.example.davidgormally.checkmatefire.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.davidgormally.checkmatefire.database.entity.FireDoor

@Dao
interface FireDoorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFireDoor(fireDoor: FireDoor)

    @Update
    suspend fun updateFireDoor(fireDoor: FireDoor)

    @Query("DELETE FROM fire_door WHERE id = :fireDoorId")
    suspend fun deleteFireDoorById(fireDoorId: String)

    @Query("SELECT * FROM fire_door")
    fun getAllFireDoors(): LiveData<List<FireDoor>>

    @Query("SELECT * FROM fire_door WHERE id = :fireDoorId")
    suspend fun getFireDoorById(fireDoorId: String): FireDoor

    /*
      Search queries
    */

    @Query("SELECT * FROM fire_door WHERE `no` = :fireDoorNumber")
    fun getFireDoorByNumber(fireDoorNumber: String): LiveData<List<FireDoor>>
}