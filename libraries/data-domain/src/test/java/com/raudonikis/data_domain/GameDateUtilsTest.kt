package com.raudonikis.data_domain

import com.raudonikis.data_domain.game_release_date.GameDateUtils
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.*

class GameDateUtilsTest {

    @Test
    fun `from string to date, correct format, returns correct date`() {
        // Assemble
        val dateString = "2000-10-15"
        // Act
        val result = GameDateUtils.fromStringToDate(dateString)
        val expected = Calendar.getInstance().apply {
            set(2000, 9, 15, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        // Assert
        assertEquals(result?.time, expected.time)
    }

    @Test
    fun `from string to date, correct format 2, returns correct date`() {
        // Assemble
        val dateString = "1995-11-05"
        // Act
        val result = GameDateUtils.fromStringToDate(dateString)
        val expected = Calendar.getInstance().apply {
            set(1995, 10, 5, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        // Assert
        assertEquals(result?.time, expected.time)
    }

    @Test
    fun `from string to date, correct format 3, returns correct date`() {
        // Assemble
        val dateString = "2020-12-23"
        // Act
        val result = GameDateUtils.fromStringToDate(dateString)
        val expected = Calendar.getInstance().apply {
            set(2020, 11, 23, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        // Assert
        assertEquals(result?.time, expected.time)
    }

    @Test
    fun `from string to date, incorrect format, returns null`() {
        // Assemble
        val dateString = "2000-s10-15"
        // Act
        val result = GameDateUtils.fromStringToDate(dateString)
        val expected = null
        // Assert
        assertEquals(result?.time, expected)
    }

    @Test
    fun `from string to date, incorrect format 2, returns null`() {
        // Assemble
        val dateString = "2000"
        // Act
        val result = GameDateUtils.fromStringToDate(dateString)
        val expected = null
        // Assert
        assertEquals(result?.time, expected)
    }
}