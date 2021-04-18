package com.raudonikis.data_domain.game_release_date

import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object GameDateUtils {

    val dateParser
        get() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val dateFormatter
        get() = DateFormat.getDateInstance()

    fun fromStringToDate(dateString: String): Date? {
        return try {
            dateParser.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    fun fromDateToFormattedString(date: Date): String {
        return dateFormatter.format(date)
    }

    fun fromStringToFormattedString(dateString: String): String {
        val date = fromStringToDate(dateString) ?: return ""
        return fromDateToFormattedString(date)
    }
}