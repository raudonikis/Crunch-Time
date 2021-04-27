package com.raudonikis.network.utils

import com.raudonikis.common.Outcome
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkResponseTest {

    @Test
    fun `toOutcome, is failure, returns Outcome_Failure`() {
        //Assemble
        val networkResponse = NetworkResponse<String>(isSuccess = false)
        val expectedResult = Outcome.failure()
        //Act
        val result = networkResponse.toOutcome()
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `toOutcome, is failure with message, returns Outcome_Failure with message`() {
        //Assemble
        val networkResponse = NetworkResponse<String>(
            isSuccess = false,
            error = NetworkErrorResponse(message = "error")
        )
        val expectedResult = Outcome.failure(message = "error")
        //Act
        val result = networkResponse.toOutcome()
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `toOutcome, is success empty, returns Outcome_Empty`() {
        //Assemble
        val networkResponse = NetworkResponse<String>(isSuccess = true, data = null)
        val expectedResult = Outcome.empty()
        //Act
        val result = networkResponse.toOutcome()
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `toOutcome, is success with data, returns Outcome_Success`() {
        //Assemble
        val data = "some data"
        val networkResponse = NetworkResponse(isSuccess = true, data = data)
        val expectedResult = Outcome.success(data)
        //Act
        val result = networkResponse.toOutcome()
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `onFailure, is failure, calls lambda`() {
        //Assemble
        val networkResponse = NetworkResponse<String>(isSuccess = false)
        val testFunction: () -> Unit = spyk()
        //Act
        networkResponse.onFailure {
            testFunction()
        }
        //Assert
        verify { testFunction() }
    }

    @Test
    fun `onFailure, is not failure, does not call lambda`() {
        //Assemble
        val networkResponse = NetworkResponse<String>(isSuccess = true)
        val testFunction: () -> Unit = spyk()
        //Act
        networkResponse.onFailure {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }

    @Test
    fun `onSuccess, is success with data, calls lambda`() {
        //Assemble
        val networkResponse = NetworkResponse<String>(isSuccess = true, data = "some data")
        val testFunction: () -> Unit = spyk()
        //Act
        networkResponse.onSuccess {
            testFunction()
        }
        //Assert
        verify{ testFunction() }
    }

    @Test
    fun `onSuccess, is not success, does not call lambda`() {
        //Assemble
        val networkResponse = NetworkResponse<String>(isSuccess = false)
        val testFunction: () -> Unit = spyk()
        //Act
        networkResponse.onSuccess {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }

    @Test
    fun `onSuccess, is success but empty, does not call lambda`() {
        //Assemble
        val networkResponse = NetworkResponse<String>(isSuccess = true, data = null)
        val testFunction: () -> Unit = spyk()
        //Act
        networkResponse.onSuccess {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }

    @Test
    fun `onEmpty, is empty, calls lambda`() {
        //Assemble
        val networkResponse = NetworkResponse<String>(isSuccess = true, data = null)
        val testFunction: () -> Unit = spyk()
        //Act
        networkResponse.onEmpty {
            testFunction()
        }
        //Assert
        verify{ testFunction() }
    }

    @Test
    fun `onEmpty, is not empty, does not call lambda`() {
        //Assemble
        val networkResponse = NetworkResponse<String>(isSuccess = true, data = "some data")
        val testFunction: () -> Unit = spyk()
        //Act
        networkResponse.onEmpty {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }
}