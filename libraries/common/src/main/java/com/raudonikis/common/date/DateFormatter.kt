package com.raudonikis.common.date

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    private val formatter
        get() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun stringToDate(dateString: String): Date? {
        return try {
            formatter.parse(dateString)
        } catch (e: Exception) {
            Timber.w("Could not parse date -> $dateString")
            null
        }
    }

    fun dateToFormattedString(date: Date): String {
        return try {
            formatter.format(date)
        } catch (e: Exception) {
            Timber.w("Could not format date -> $date")
            ""
        }
    }
}