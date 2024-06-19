package com.epamupskills.booknotes.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.epamupskills.booknotes.core.abstraction.UserPreferences
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : UserPreferences {

    private val data = dataStore.data.map { prefs ->
        prefs[stringPreferencesKey(CURRENT_USER_ID_KEY)]
    }

    override suspend fun getUserId(): String = data.filterNotNull().first()

    override suspend fun saveUserId(uid: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(CURRENT_USER_ID_KEY)] = uid
        }
    }

    companion object {
        private const val CURRENT_USER_ID_KEY = "CURRENT_USER_ID_KEY"
    }
}