package com.example.rickandmorty.prefs


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AppDatastore @Inject constructor (private val dataStore: DataStore<Preferences>) {

        private object DataStoreKeys{
            val isFirstRun = booleanPreferencesKey(Constants.FIRST_RUN_KEY)
        }

        suspend fun saveRunInfo(isFirst: Boolean){
            dataStore.edit {
                it[DataStoreKeys.isFirstRun] = isFirst
            }
        }

         suspend fun readRunInfo(): Boolean{
            val p = dataStore.data.first()
            return p[DataStoreKeys.isFirstRun]?:false
        }
}