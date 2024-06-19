package com.epamupskills.booknotes.core.abstraction

interface UidRepository {
    suspend fun getUserId(): String
    suspend fun saveUserId(uid: String)
}