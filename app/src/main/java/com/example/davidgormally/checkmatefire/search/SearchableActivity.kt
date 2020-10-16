package com.example.davidgormally.checkmatefire.search

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.davidgormally.checkmatefire.ServiceLocator
import com.example.davidgormally.checkmatefire.firedoorlist.FireDoorListViewModel
import com.example.davidgormally.checkmatefire.firedoorlist.FireDoorListViewModelFactory
import java.util.*

class SearchableActivity : AppCompatActivity() {

    private val viewModel by viewModels<FireDoorListViewModel> {
        FireDoorListViewModelFactory(ServiceLocator.provideRepository(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (Intent.ACTION_SEARCH == intent.action) {
//            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
//                viewModel.searchFireDoor(query)
//            }
//        }
    }

}
