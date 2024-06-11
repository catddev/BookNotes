package com.epamupskills.booknotes

import com.epamupskills.authorization.domain.UserCredentialsValidator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class UserCredentialsValidatorTest {

    private val validator = UserCredentialsValidator()

    @ParameterizedTest
    @CsvSource(
        "user123@gmail.com, true",
        "user.com, false",
        "user@.com, false",
        "user@!!!com, false",
    )
    fun `should return TRUE when user email matches restrictions and FALSE when don't`(
        email: String,
        expected: Boolean
    ) {
        val actual = validator.validateEmail(email)
        Assertions.assertEquals(expected, actual)
    }

    @ParameterizedTest
    @CsvSource(
        "Abc123, true",
        "Abc123, true",
        "12345abCD, true",
        "Abc, false",
        "Abc1 2, false",
        "Abc12, false",
        "abc123, false",
        "ABC123, false",
        "Abc12345!!!, false",
        "Abc!!!, false",
    )
    fun `should return TRUE when user password matches restrictions and FALSE when don't`(
        password: String,
        expected: Boolean
    ) {
        val actual = validator.validatePassword(password)
        Assertions.assertEquals(expected, actual)
    }

    @ParameterizedTest
    @CsvSource(
        "Abc12345, Abc12345, true",
        "Abc12345, Abc, false",
    )
    fun `should return TRUE when password is confirmed correctly and FALSE when it is not`(
        password: String,
        passwordConfirmed: String,
        expected: Boolean
    ) {
        val actual = validator.validatePasswordConfirmation(password, passwordConfirmed)
        Assertions.assertEquals(expected, actual)
    }
}