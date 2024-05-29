package com.epamupskills.authorization.presentation.auth_shared

import com.epamupskills.authorization.presentation.models.UserCredentialsUi

data class AuthViewState(
    val userCredentials: UserCredentialsUi = UserCredentialsUi("", ""),
    val confirmedPassword: String = "",
    val isSignInButtonEnabled: Boolean = false,
    val isSignUpButtonEnabled: Boolean = false,
)