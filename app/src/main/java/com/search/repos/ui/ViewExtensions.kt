package com.search.repos.ui

import android.view.View
import android.widget.ProgressBar

/**
 * Invisible loading bar after API call finish
 */
fun ProgressBar.hide() {
    visibility = View.GONE
}

/**
 * Visible loading bar after API call starts
 */
fun ProgressBar.show() {
    visibility = View.VISIBLE
}

