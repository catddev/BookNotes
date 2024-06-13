package com.epamupskills.booknotes.authorization.domain.models

data class UserCredentials(
    val email: String,
    val password: String,
)