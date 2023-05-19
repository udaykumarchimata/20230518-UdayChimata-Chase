package com.example.weather

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {

    fun setStringInfo(context: Context, key: String, value: String) {
        val editor: SharedPreferences.Editor = context.getSharedPreferences(
            "SharedPreferences", Context.MODE_PRIVATE
        ).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringInfo(context: Context, key: String, defaultValue: String = ""): String {
        return context.getSharedPreferences(
            "SharedPreferences", Context.MODE_PRIVATE
        ).getString(key, defaultValue)!!
    }
}