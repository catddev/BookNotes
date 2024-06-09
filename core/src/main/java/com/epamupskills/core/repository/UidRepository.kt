package com.epamupskills.core.repository

interface UidRepository {
    suspend fun getUserId(): String
    suspend fun saveUserId(uid: String)
}