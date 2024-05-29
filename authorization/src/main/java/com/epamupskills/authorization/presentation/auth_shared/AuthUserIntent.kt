package com.epamupskills.authorization.presentation.auth_shared

import com.epamupskills.core.UserIntent

sealed interface AuthUserIntent: UserIntent

data class EnterEmail(val email: String) : AuthUserIntent
data class EnterPassword(val password: String) : AuthUserIntent
data class ConfirmPassword(val passwordConfirmed: String) : AuthUserIntent
data object Confirm : AuthUserIntent
data object SignUp : AuthUserIntent
data object SignIn : AuthUserIntent
data object SignOut : AuthUserIntent
data object DeleteAccount : AuthUserIntent