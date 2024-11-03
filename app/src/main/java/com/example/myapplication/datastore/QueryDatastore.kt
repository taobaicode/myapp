package com.example.myapplication.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QueryDatastore @Inject
    constructor(@ApplicationContext val context : Context) {
    private val Context.queryPreferenceDataStore by preferencesDataStore("query")

    val query : Flow<String?>
        get() = context.queryPreferenceDataStore.data.map {preferences ->
            preferences[queryKey]
        }

    suspend fun updateQuery(query: String) {
        context.queryPreferenceDataStore.edit { preferences ->
            preferences[queryKey] = query
        }
    }

    companion object {
        private val queryKey = stringPreferencesKey("query")
    }
}