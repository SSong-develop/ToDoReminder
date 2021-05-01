package com.hks.kr.wifireminder.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.work.impl.model.Preference
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PrefsStoreImpl @Inject constructor(@ApplicationContext context : Context) : PrefsStore {
    private val dataStore = context.createDataStore(
        name = STORE_NAME
    )

    override suspend fun isAlreadyWorked(): Boolean = runCatching {
        val pref = dataStore.data.first()
        pref[IS_ALREADY_DONE_IT_BEFORE] == true
    }.getOrDefault(false)

    override suspend fun runOnlyOnce(){
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