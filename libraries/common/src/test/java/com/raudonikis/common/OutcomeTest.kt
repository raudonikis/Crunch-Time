package com.raudonikis.common

import io.mockk.spyk
import io.mockk.verify
import org.junit.Test

class OutcomeTest {

    @Test
    fun `onFailure, is failure, calls lambda`() {
        //Assemble
        val outcome = Outcome.failure()
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onFailure {
            testFunction()
        }
        //Assert
        verify { testFunction() }
    }

    @Test
    fun `onFailure, is empty, does not call lambda`() {
        //Assemble
        val outcome = Outcome.empty()
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onFailure {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }

    @Test
    fun `onFailure, is success, does not call lambda`() {
        //Assemble
        val outcome = Outcome.success("data")
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onFailure {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }

    @Test
    fun `onFailure, is loading, does not call lambda`() {
        //Assemble
        val outcome = Outcome.loading()
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onFailure {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }

    @Test
    fun `onSuccess, is failure, does not call lambda`() {
        //Assemble
        val outcome = Outcome.failure()
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onSuccess {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }

    @Test
    fun `onSuccess, is empty, does not call lambda`() {
        //Assemble
        val outcome = Outcome.empty()
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onSuccess {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }

    @Test
    fun `onSuccess, is success, calls lambda`() {
        //Assemble
        val outcome = Outcome.success("data")
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onSuccess {
            testFunction()
        }
        //Assert
        verify { testFunction() }
    }

    @Test
    fun `onSuccess, is loading, does not call lambda`() {
        //Assemble
        val outcome = Outcome.loading()
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onSuccess {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }

    @Test
    fun `onEmpty, is failure, does not call lambda`() {
        //Assemble
        val outcome = Outcome.failure()
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onEmpty {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }

    @Test
    fun `onEmpty, is empty, calls lambda`() {
        //Assemble
        val outcome = Outcome.empty()
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onEmpty {
            testFunction()
        }
        //Assert
        verify { testFunction() }
    }

    @Test
    fun `onEmpty, is success, does not call lambda`() {
        //Assemble
        val outcome = Outcome.success("data")
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onEmpty {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }

    @Test
    fun `onEmpty, is loading, does not call lambda`() {
        //Assemble
        val outcome = Outcome.loading()
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onEmpty {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }

    @Test
    fun `onLoading, is failure, does not call lambda`() {
        //Assemble
        val outcome = Outcome.failure()
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onLoading {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }

    @Test
    fun `onLoading, is empty, does not call lambda`() {
        //Assemble
        val outcome = Outcome.empty()
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onLoading {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }

    @Test
    fun `onLoading, is success, does not call lambda`() {
        //Assemble
        val outcome = Outcome.success("data")
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onLoading {
            testFunction()
        }
        //Assert
        verify(exactly = 0) { testFunction() }
    }

    @Test
    fun `onLoading, is loading, calls lambda`() {
        //Assemble
        val outcome = Outcome.loading()
        val testFunction: () -> Unit = spyk()
        //Act
        outcome.onLoading {
            testFunction()
        }
        //Assert
        verify { testFunction() }
    }
}