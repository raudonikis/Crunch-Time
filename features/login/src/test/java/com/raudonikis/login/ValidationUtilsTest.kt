package com.raudonikis.login

import com.raudonikis.login.validation.EmailState
import com.raudonikis.login.validation.PasswordState
import com.raudonikis.login.validation.UsernameState
import com.raudonikis.login.validation.ValidationUtils
import junit.framework.Assert.assertEquals
import org.junit.Test

class ValidationUtilsTest {

    @Test
    fun `validateEmail, valid email, returns Valid`() {
        //Assemble
        val emailToValidate = "email@email.com"
        val expectedResult = EmailState.Valid(emailToValidate)
        //Act
        val result = ValidationUtils.validateEmail(emailToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validateEmail, valid email 2, returns Valid`() {
        //Assemble
        val emailToValidate = "email.email@email.org"
        val expectedResult = EmailState.Valid(emailToValidate)
        //Act
        val result = ValidationUtils.validateEmail(emailToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validateEmail, null, returns Blank`() {
        //Assemble
        val emailToValidate = null
        val expectedResult = EmailState.Blank
        //Act
        val result = ValidationUtils.validateEmail(emailToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validateEmail, empty, returns Blank`() {
        //Assemble
        val emailToValidate = ""
        val expectedResult = EmailState.Blank
        //Act
        val result = ValidationUtils.validateEmail(emailToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validateEmail, blank, returns Blank`() {
        //Assemble
        val emailToValidate = "   "
        val expectedResult = EmailState.Blank
        //Act
        val result = ValidationUtils.validateEmail(emailToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validateEmail, no email domain, returns BadFormat`() {
        //Assemble
        val emailToValidate = "email.com"
        val expectedResult = EmailState.BadFormat(emailToValidate)
        //Act
        val result = ValidationUtils.validateEmail(emailToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validateEmail, no user name, returns BadFormat`() {
        //Assemble
        val emailToValidate = "@email.com"
        val expectedResult = EmailState.BadFormat(emailToValidate)
        //Act
        val result = ValidationUtils.validateEmail(emailToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validateEmail, no domain, returns BadFormat`() {
        //Assemble
        val emailToValidate = "email@email"
        val expectedResult = EmailState.BadFormat(emailToValidate)
        //Act
        val result = ValidationUtils.validateEmail(emailToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validateEmail, no domain 2, returns BadFormat`() {
        //Assemble
        val emailToValidate = "email@email."
        val expectedResult = EmailState.BadFormat(emailToValidate)
        //Act
        val result = ValidationUtils.validateEmail(emailToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validateUsername, not empty, returns Valid`() {
        //Assemble
        val usernameToValidate = "username"
        val expectedResult = UsernameState.Valid(usernameToValidate)
        //Act
        val result = ValidationUtils.validateUsername(usernameToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validateUsername, empty, returns Blank`() {
        //Assemble
        val usernameToValidate = ""
        val expectedResult = UsernameState.Blank
        //Act
        val result = ValidationUtils.validateUsername(usernameToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validateUsername, blank, returns Blank`() {
        //Assemble
        val usernameToValidate = "    "
        val expectedResult = UsernameState.Blank
        //Act
        val result = ValidationUtils.validateUsername(usernameToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validateUsername, null, returns Blank`() {
        //Assemble
        val usernameToValidate = null
        val expectedResult = UsernameState.Blank
        //Act
        val result = ValidationUtils.validateUsername(usernameToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validatePassword, empty, returns Blank`() {
        //Assemble
        val passwordToValidate = ""
        val expectedResult = PasswordState.Blank
        //Act
        val result = ValidationUtils.validatePassword(passwordToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validatePassword, blank, returns Blank`() {
        //Assemble
        val passwordToValidate = "    "
        val expectedResult = PasswordState.Blank
        //Act
        val result = ValidationUtils.validatePassword(passwordToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validatePassword, null, returns Blank`() {
        //Assemble
        val passwordToValidate = null
        val expectedResult = PasswordState.Blank
        //Act
        val result = ValidationUtils.validatePassword(passwordToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validatePassword, short, returns TooShort`() {
        //Assemble
        val passwordToValidate = "pa"
        val expectedResult = PasswordState.TooShort(passwordToValidate)
        //Act
        val result = ValidationUtils.validatePassword(passwordToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validatePassword, short 2, returns TooShort`() {
        //Assemble
        val passwordToValidate = "passw"
        val expectedResult = PasswordState.TooShort(passwordToValidate)
        //Act
        val result = ValidationUtils.validatePassword(passwordToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `validatePassword, valid, returns Valid`() {
        //Assemble
        val passwordToValidate = "password"
        val expectedResult = PasswordState.Valid(passwordToValidate)
        //Act
        val result = ValidationUtils.validatePassword(passwordToValidate)
        //Assert
        assertEquals(expectedResult, result)
    }
}