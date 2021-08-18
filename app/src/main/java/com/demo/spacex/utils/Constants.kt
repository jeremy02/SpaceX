package com.demo.spacex.utils

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object Constants {
    // get a view model dynamically
    inline fun <reified T : ViewModel> getFragViewModel(context: FragmentActivity) =
        ViewModelProvider(context).get(T::class.java)
}