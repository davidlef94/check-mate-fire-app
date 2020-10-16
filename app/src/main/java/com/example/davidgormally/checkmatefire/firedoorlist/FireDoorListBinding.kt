package com.example.davidgormally.checkmatefire.firedoorlist

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.davidgormally.checkmatefire.database.entity.FireDoor
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("items")
fun setItems(listView: RecyclerView, fireDoors: List<FireDoor>?) {
    fireDoors?.let {
        (listView.adapter as FireDoorListAdapter).submitList(fireDoors)
    }
}

@BindingAdapter("dateFormat")
fun setDateFormat(textView: TextView, creationDate: Date) {
    val simpleDateFormat = SimpleDateFormat("dd-MM-YYYY", Locale.UK)
    val creationDateValue = simpleDateFormat.format(creationDate)
    textView.text = creationDateValue
}

@BindingAdapter("doorLocation")
fun setDoorLocationName(textView: TextView, fireDoorPosition: Int) {
    val doorLocations = arrayOf(
        "3rd Floor - Pauling Close",
        "2nd Floor - JC Street",
        "2nd Floor - Nobel Square",
        "1st Floor - Bragg Avenue",
        "1st Floor - Davis Grove",
        "Ground - NHS",
        "Ground - Day Center",
        "Ground - Main Building"
    )

    textView.text = doorLocations[fireDoorPosition]
}