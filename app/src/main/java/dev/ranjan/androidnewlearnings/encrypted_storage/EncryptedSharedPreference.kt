package dev.ranjan.androidnewlearnings.encrypted_storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


class EncryptedSharedPreference(context: Context) {

    /*
    Initializing preference without using any encryption.
    private val pref = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    */

    private val masterKey: MasterKey =
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    //EncryptedSharedPreferences is a android x library which help us to create a shared preference in encrypted way.
    private val pref: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secret_shared_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun putString(key: String, value: String?) {
        pref.edit().putString(key, value).apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        pref.edit().putBoolean(key, value).apply()
    }

    fun putInt(key: String, value: Int) {
        pref.edit().putInt(key, value).apply()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean = pref.getBoolean(key, defValue)

    fun getInt(key: String, defValue: Int): Int = pref.getInt(key, defValue)

    fun getString(key: String, defValue: String?): String? = pref.getString(key, defValue)

    fun clearSharedPref() {
        pref.edit().clear().apply()
    }

}
