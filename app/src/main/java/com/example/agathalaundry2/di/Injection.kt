package com.example.agathalaundry2.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.agathalaundry2.data.Repository
import com.example.agathalaundry2.data.local.AuthPreferences
import com.example.agathalaundry2.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(dataStore: DataStore<Preferences>): Repository {
        val apiService = ApiConfig.getApiService()
        val authPreferences = AuthPreferences.getInstance(dataStore)
        return Repository.getInstance(apiService, authPreferences)
    }
}