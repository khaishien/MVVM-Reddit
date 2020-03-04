package com.nexus.boosttestapp.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        fun formatDateFromLong(longDate: Long): String {
            val date = Date(longDate)
            var df2 = SimpleDateFormat("dd MMM yyyy")
            return df2.format(date)
        }
    }
}