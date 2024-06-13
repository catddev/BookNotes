package com.epamupskills.booknotes.authorization.presentation.auth_shared

import com.epamupskills.booknotes.authorization.presentation.models.UserCredentialsUi
import com.epamupskills.booknotes.core.Constants.EMPTY

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