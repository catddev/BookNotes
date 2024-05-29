package com.epamupskills.authorization.domain

import androidx.core.util.PatternsCompat
import com.epamupskills.authorization.domain.models.UserCredentials
import javax.inject.Inject

class UserCredentialsValidator @Inject constructor() {

    operator fun invoke(userCredentials: UserCredentials): Boolean = validate(
        mapOf(
            ValidationType.EMAIL_REGEX to userCredentials.email,
            ValidationType.PASSWORD_REGEX to userCredentials.password,
        )
    )

    operator fun invoke(userCredentials: UserCredentials, passwordConfirmed: String): Boolean =
        invoke(userCredentials) && userCredentials.password.trim() == passwordConfirmed.trim()

    private fun validate(map: Map<ValidationType, String>): Boolean =
        map.all { (regex, value) -> regex.pattern.matches(value.trim()) }

    private enum class ValidationType(val pattern: Regex) {
        EMAIL_REGEX(PatternsCompat.EMAIL_ADDRESS.toRegex()),
        PASSWORD_REGEX("(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])[a-zA-Z0-9]{6,}".toRegex())
    }
}