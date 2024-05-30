package com.epamupskills.authorization.domain

import androidx.core.util.PatternsCompat
import javax.inject.Inject

class UserCredentialsValidator @Inject constructor() {

    fun validateEmail(email: String): Boolean = validate(ValidationType.EMAIL_REGEX, email)

    fun validatePassword(password: String): Boolean =
        validate(ValidationType.PASSWORD_REGEX, password)

    fun validatePasswordConfirmation(password: String, passwordConfirmation: String): Boolean =
        password.trim() == passwordConfirmation.trim()

    private fun validate(regex: ValidationType, value: String): Boolean =
        regex.pattern.matches(value.trim())

    private enum class ValidationType(val pattern: Regex) {
        EMAIL_REGEX(PatternsCompat.EMAIL_ADDRESS.toRegex()),
        PASSWORD_REGEX("(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])[a-zA-Z0-9]{6,}".toRegex())
    }
}