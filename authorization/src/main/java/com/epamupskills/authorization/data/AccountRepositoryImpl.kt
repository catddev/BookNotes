package com.epamupskills.authorization.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.epamupskills.authorization.domain.AccountRepository
import com.epamupskills.authorization.domain.models.UserCredentials
import com.epamupskills.core.PreferencesSettings.CURRENT_USER_ID_KEY
import com.epamupskills.core.PreferencesSettings.IS_ACCOUNT_DELETED_KEY
import com.epamupskills.core.di.IO
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class AccountRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val externalScope: CoroutineScope,
    @IO private val dispatcherIo: CoroutineDispatcher,
) : AccountRepository {

    private val _isAuth = MutableStateFlow(Firebase.auth.currentUser != null)
    override val isAuth = _isAuth.asStateFlow()

    init {
        val scope = CoroutineScope(dispatcherIo)
        scope.produce<Unit> {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                _isAuth.value = auth.currentUser != null
                launch {
                    onFetchUserId(auth.uid)
                }
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
        withContext(dispatcherIo) {
            Firebase.auth
                .signInWithEmailAndPassword(userCredentials.email, userCredentials.password)
                .await()
        }

    override suspend fun signUp(userCredentials: UserCredentials): Unit =
        withContext(dispatcherIo) {
            Firebase.auth
                .createUserWithEmailAndPassword(userCredentials.email, userCredentials.password)
                .await()
        }

    override suspend fun signOut(): Unit = withContext(dispatcherIo) {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount(): Unit = withContext(dispatcherIo) {
        Firebase.auth.currentUser?.run { delete().await() }
        dataStore.edit { prefs ->
            prefs[booleanPreferencesKey(IS_ACCOUNT_DELETED_KEY)] = true
        }
    }

    private suspend fun onFetchUserId(uid: String?) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(CURRENT_USER_ID_KEY)] = uid.orEmpty()
        }
    }
}