package com.epamupskills.booknotes.authorization.data

import com.epamupskills.booknotes.core.abstraction.UidRepository
import com.epamupskills.booknotes.core.abstraction.UserPreferences
import com.epamupskills.booknotes.di.IO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UidRepositoryImpl @Inject constructor(
    private val userPreferences: UserPreferences,
    @IO private val dispatcherIo: CoroutineDispatcher, //todo delete dispatcher injection everywhere
) : UidRepository {

    override suspend fun getUserId(): String = withContext(dispatcherIo) {
        userPreferences.getUserId()
    }
    override suspend fun saveUserId(uid: String) = withContext(dispatcherIo){
        userPreferences.saveUserId(uid)
    }
}