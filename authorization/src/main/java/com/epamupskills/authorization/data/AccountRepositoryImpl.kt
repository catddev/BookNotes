package com.epamupskills.authorization.data

import com.epamupskills.authorization.domain.AccountRepository
import com.epamupskills.authorization.domain.models.UserCredentials
import com.epamupskills.core.di.IO
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @IO private val dispatcherIo: CoroutineDispatcher,
) : AccountRepository {

    override val isAuth
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                trySend(auth.currentUser != null)
            }

            firebaseAuth.addAuthStateListener(listener)
            awaitClose {
                firebaseAuth.removeAuthStateListener(listener)
            }
        }

    override fun getUserEmail(): String = firebaseAuth.currentUser?.email.orEmpty()

    override fun getUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()

    override suspend fun signIn(userCredentials: UserCredentials): Unit =
        withContext(dispatcherIo) {
            firebaseAuth
                .signInWithEmailAndPassword(userCredentials.email, userCredentials.password)
                .await()
        }

    override suspend fun signUp(userCredentials: UserCredentials): Unit =
        withContext(dispatcherIo) {
            firebaseAuth
                .createUserWithEmailAndPassword(userCredentials.email, userCredentials.password)
                .await()
        }

    override suspend fun signOut(): Unit = withContext(dispatcherIo) {
        firebaseAuth.signOut()
    }

    override suspend fun deleteAccount(): Unit = withContext(dispatcherIo) {
        firebaseAuth.currentUser?.run { delete().await() }
    }
}