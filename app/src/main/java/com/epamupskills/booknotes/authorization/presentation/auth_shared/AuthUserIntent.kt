package com.epamupskills.booknotes.authorization.presentation.auth_shared

import com.epamupskills.booknotes.core.UserIntent

sealed interface AuthUserIntent: UserIntent

data class EnterEmail(val email: String) : AuthUserIntent
data class EnterPassword(val password: String) : AuthUserIntent
data class ConfirmPassword(val passwordConfirmed: String) : AuthUserIntent
data object Confirm : AuthUserIntent
data object SignUp : AuthUserIntent
data object SignIn : AuthUserIntent
data object SignOut : AuthUserIntent
data object DeleteAccount : AuthUserIntent