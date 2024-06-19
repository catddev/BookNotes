package com.epamupskills.booknotes.core.abstraction

interface UserPreferences {
    suspend fun getUserId(): String
    suspend fun saveUserId(uid: String)
}