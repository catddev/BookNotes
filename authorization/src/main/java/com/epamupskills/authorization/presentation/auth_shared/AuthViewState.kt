package com.epamupskills.authorization.presentation.auth_shared

import com.epamupskills.authorization.presentation.models.UserCredentialsUi
import com.epamupskills.core.Constants.EMPTY

data class AuthViewState(
    val userCredentials: UserCredentialsUi = UserCredentialsUi(EMPTY, EMPTY),
    val confirmedPassword: String = EMPTY,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isPasswordConfirmedCorrectly: Boolean = false,
) {
    val isSignInButtonEnabled: Boolean = isEmailValid && isPasswordValid
    val isSignUpButtonEnabled: Boolean =
        isEmailValid && isPasswordValid && isPasswordConfirmedCorrectly
}