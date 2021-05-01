package com.hks.kr.wifireminder.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class PrefsStoreImpl @Inject constructor(@ApplicationContext context: Context) : PrefsStore {
    private val dataStore = context.createDataStore(
        name = STORE_NAME
    )

    override suspend fun isAlreadySingleInvoked(): Boolean = runCatching {
        val pref = dataStore.data.first()
        pref[IS_ALREADY_DONE_IT_BEFORE] == true
    }.getOrDefault(false)

    override suspend fun isAlreadySingleInvokedWithFlow(): Flow<Boolean> =
        dataStore.data.catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { prefs ->
            prefs[IS_ALREADY_DONE_IT_BEFORE] == true
        }

    override suspend fun onSingleInvoke() {
        runCatching {
            dataStore.edit { pref ->
                pref[IS_ALREADY_DONE_IT_BEFORE] = true
            }
        }
    }

    companion object {
        private const val STORE_NAME = "use_once_code_store"
        private val IS_ALREADY_DONE_IT_BEFORE = preferencesKey<Boolean>("isAlreadyDoneItBefore")
    }
}