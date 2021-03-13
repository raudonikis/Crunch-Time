package com.raudonikis.data_domain.game_release_date

import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object GameDateUtils {

    private fun fromStringToDate(dateString: String): Date? {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            format.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    private fun fromDateToFormattedString(date: Date): String {
        return DateFormat.getDateInstance().format(date)
    }

    fun fromStringToFormattedString(dateString: String): String {
        val date = fromStringToDate(dateString) ?: return ""
        return fromDateToFormattedString(date)
    }
}