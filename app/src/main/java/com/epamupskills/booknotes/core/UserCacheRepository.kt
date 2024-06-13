package com.epamupskills.booknotes.core

interface UserCacheRepository {
    suspend fun clearCachedUserData(userId: String)
}