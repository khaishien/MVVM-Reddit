package com.nexus.boosttestapp.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        fun formatDateFromUnix(unixDate: Int): String {
            Log.d("TTTTT", unixDate.toString())
            val date = Date(unixDate * 1000L)
            var df2 = SimpleDateFormat("dd MMM yyyy")
            return df2.format(date)
        }
    }
}