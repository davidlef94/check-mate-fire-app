package com.example.davidgormally.checkmatefire.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "fire_door")
data class FireDoor (
    @PrimaryKey @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "no") var no: String = "",
    @ColumnInfo(name = "location") var location: Int = 0, // save the position of door location selected on spinner
    @ColumnInfo(name = "labelled") var labelled: Boolean = false,
    @ColumnInfo(name = "top_max") var topMax: String = "",
    @ColumnInfo(name = "bottom_max") var bottomMax: String = "",
    @ColumnInfo(name = "left_max") var leftMax: String = "",
    @ColumnInfo(name = "right_max") var rightMax: String = "",
    @ColumnInfo(name = "instrumescent_seal") var instrumescentSeal: Boolean = false,
    @ColumnInfo(name = "three_hinges") var threeHinges: Boolean = false,
    @ColumnInfo(name = "door_closures_work") var doorClosuresWork: Boolean = false,
    @ColumnInfo(name = "glazing_and_glass") var glazingAndGlass: Boolean = false,
    @ColumnInfo(name = "door_leaf_ok") var doorLeafOk: Boolean = false,
    @ColumnInfo(name = "door_frame_ok") var doorFrameOk: Boolean = false,
    @ColumnInfo(name = "door_operation_ok") var doorOperationOk: Boolean = false,
    @ColumnInfo(name = "comments") var comments: String = "",
    @ColumnInfo(name = "action_required") var actionRequired: String = "",
    @ColumnInfo(name = "date_created") val dateCreated: Date = Date()
)