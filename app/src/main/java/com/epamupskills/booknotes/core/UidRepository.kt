package com.epamupskills.booknotes.core

interface UidRepository {
    suspend fun getUserId(): String
    suspend fun saveUserId(uid: String)
}