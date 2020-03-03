package com.nexus.boosttestapp.utils

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ViewModelUtils {

    companion object {
        fun <T : ViewModel?> getViewModel(
            activity: FragmentActivity?,
            tClass: Class<T>
        ): T? {
            return tClass.cast(
                ViewModelProvider(activity!!)[tClass]
            )
        }
    }

}