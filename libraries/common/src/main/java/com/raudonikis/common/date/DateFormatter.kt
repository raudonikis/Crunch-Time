package com.raudonikis.common.date

import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    //2021-02-28T20:06:55.000000Z
    private val parser: DateFormat
        get() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    private val formatter: DateFormat
        get() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun stringToDate(dateString: String): Date? {
        return try {
            parser.parse(dateString)
        } catch (e: Exception) {
            Timber.w("Could not parse date -> $dateString, exception -> ${e.message}")
            null
        }
    }

    fun dateToFormattedString(date: Date): String {
        return try {
            formatter.format(date)
        } catch (e: Exception) {
            Timber.w("Could not format date -> $date, exception -> ${e.message}")
            ""
        }
    }
}