package com.task.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ahmedeltaher on 08/01/2018.
 */


object DateUtil {
    const val SERVER_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val APP_DATE_PATTERN = "dd.MM.yyyy"

    private fun parseStringDate(serverDate: String): Calendar {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat(SERVER_DATE_PATTERN, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        try {
            cal.time = sdf.parse(serverDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return cal
    }

    private fun parseDateToReadableFormat(calendar: Calendar?): String? {
        var dateServerFormat: String? = null
        val formatter = SimpleDateFormat(APP_DATE_PATTERN, Locale.ENGLISH)
        calendar?.let {
            dateServerFormat = formatter.format(it.time)
        }
        return dateServerFormat
    }

    fun parseDate(serverDate: String): String? {
        return parseDateToReadableFormat(parseStringDate(serverDate))
    }
}

