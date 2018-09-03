package br.com.carmo.wallison.task.utils

import android.content.Context
import android.content.SharedPreferences

class SecurityPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("task", Context.MODE_PRIVATE)

    fun storeString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getStrorage(key: String): String {
        return sharedPreferences.getString(key, "")
    }

    fun removeStorageString(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}