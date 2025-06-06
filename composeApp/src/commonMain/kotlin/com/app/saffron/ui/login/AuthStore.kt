package com.app.saffron.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.app.saffron.PrefsDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

class AuthStore(
    private val dataStore: PrefsDataStore
) {

    companion object {
        private const val IS_LOGIN = "is_login"


    }
    fun getIsLogin(): Flow<Boolean> {
        return dataStore.data.map {
            val state = it[booleanPreferencesKey(IS_LOGIN)] ?: false
            state
        }.flowOn(Dispatchers.IO)
    }

    suspend fun toggleLogin(mode: Boolean) {
        return withContext(Dispatchers.IO) {
            try {
                dataStore.edit {
                    it[booleanPreferencesKey(IS_LOGIN)] = mode
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                e.printStackTrace()
            }
        }
    }
}

@Composable
fun rememberAuthSource(prefs: PrefsDataStore): AuthStore {
    return remember { AuthStore(prefs) }
}