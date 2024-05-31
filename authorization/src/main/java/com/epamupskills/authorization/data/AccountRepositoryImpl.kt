package com.epamupskills.authorization.data

import com.epamupskills.authorization.domain.AccountRepository
import com.epamupskills.authorization.domain.models.UserCredentials
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class AccountRepositoryImpl @Inject constructor() : AccountRepository {

    private val _isAuth = MutableStateFlow(Firebase.auth.currentUser != null)
    override val isAuth = _isAuth.asStateFlow()

    init {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.produce<Unit> {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                _isAuth.value = auth.currentUser != null
            }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose {
                Firebase.auth.removeAuthStateListener(listener)
                scope.cancel()
            }
        }
    }

    override fun getUserEmail(): String = Firebase.auth.currentUser?.email.orEmpty()

    override suspend fun signIn(userCredentials: UserCredentials): Unit =
        withContext(Dispatchers.IO) {
            Firebase.auth
                .signInWithEmailAndPassword(userCredentials.email, userCredentials.password)
                .await()
        }

    override suspend fun signUp(userCredentials: UserCredentials): Unit =
        withContext(Dispatchers.IO) {
            Firebase.auth
                .createUserWithEmailAndPassword(userCredentials.email, userCredentials.password)
                .await()
        }

    override suspend fun signOut(): Unit = withContext(Dispatchers.IO) {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount(): Unit = withContext(Dispatchers.IO) {
        Firebase.auth.currentUser?.run { delete().await() }
    }
}