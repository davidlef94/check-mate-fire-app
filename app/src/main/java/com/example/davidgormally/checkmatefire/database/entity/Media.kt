package com.example.davidgormally.checkmatefire.database.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.*

@Entity(
    tableName = "media",
    foreignKeys = [
        ForeignKey(entity = FireDoor::class, parentColumns = ["id"], childColumns = ["owner_id"], onDelete = CASCADE)
    ],
    indices = [Index("owner_id")]
)
data class Media (
    @ColumnInfo(name = "owner_id") val ownerId: String = "",
    @ColumnInfo(name = "file_name") val fileName: String = "",
    @ColumnInfo(name = "date_created") val dateCreated: Date = Date()
) {
    @PrimaryKey @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString()
}