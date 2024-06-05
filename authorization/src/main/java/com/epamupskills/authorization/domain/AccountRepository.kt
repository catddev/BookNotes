package com.epamupskills.authorization.domain

import com.epamupskills.authorization.domain.models.UserCredentials
import kotlinx.coroutines.flow.Flow


interface AccountRepository {

    val isAuth: Flow<Boolean>
    fun getUserEmail(): String
    fun getUserId(): String
    suspend fun signIn(userCredentials: UserCredentials)
    suspend fun signUp(userCredentials: UserCredentials)
    suspend fun signOut()
    suspend fun deleteAccount()
}