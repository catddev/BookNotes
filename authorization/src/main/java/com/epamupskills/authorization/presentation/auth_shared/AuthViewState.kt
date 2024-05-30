package com.epamupskills.authorization.presentation.auth_shared

import com.epamupskills.authorization.presentation.models.UserCredentialsUi

data class AuthViewState(
    val userCredentials: UserCredentialsUi = UserCredentialsUi("", ""),
    val confirmedPassword: String = "",
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isPasswordConfirmedCorrectly: Boolean = false,
    val isSignInButtonEnabled: Boolean = false,
    val isSignUpButtonEnabled: Boolean = false,
)