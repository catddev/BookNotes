package com.epamupskills.booknotes.authorization.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.epamupskills.booknotes.core.PreferencesSettings
import com.epamupskills.booknotes.core.abstraction.UidRepository
import com.epamupskills.booknotes.di.IO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UidRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @IO private val dispatcherIo: CoroutineDispatcher,
) : UidRepository {

    private val _userIdFlow = MutableStateFlow<String?>(null)

    init {
        dataStore.data.map { prefs ->
            prefs[stringPreferencesKey(PreferencesSettings.CURRENT_USER_ID_KEY)]
        }.flowOn(dispatcherIo)
            .onEach { _userIdFlow.value = it }
            .launchIn(CoroutineScope(dispatcherIo))
    }

    override suspend fun getUserId(): String = _userIdFlow.filterNotNull().first()

    override suspend fun saveUserId(uid: String): Unit = withContext(dispatcherIo) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(PreferencesSettings.CURRENT_USER_ID_KEY)] = uid
        }
    }
}