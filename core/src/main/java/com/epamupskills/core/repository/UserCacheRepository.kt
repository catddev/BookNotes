package com.epamupskills.core.repository

interface UserCacheRepository {
    suspend fun clearCachedUserData(userId: String)
}