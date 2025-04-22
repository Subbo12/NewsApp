package com.example.newsapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


fun String.encode(): String = java.net.URLEncoder.encode(this, "UTF-8")
fun String.decode(): String = java.net.URLDecoder.decode(this, "UTF-8")


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val FAVORITE_CATEGORIES_KEY = stringPreferencesKey("favorite_categories")
