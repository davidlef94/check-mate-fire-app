package com.example.davidgormally.checkmatefire.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Spinner
import androidx.fragment.app.Fragment

fun Fragment.hideKeyBoard() {
    view?.let { activity?.hideKeyBoard(it) }
}

fun Context.hideKeyBoard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}