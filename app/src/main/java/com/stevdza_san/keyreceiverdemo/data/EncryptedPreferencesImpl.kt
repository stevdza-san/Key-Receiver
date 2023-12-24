package com.stevdza_san.keyreceiverdemo.data

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.stevdza_san.keyreceiverdemo.domain.EncryptedPreferences
import com.stevdza_san.keyreceiverdemo.model.Keys

const val PREF_NAME = "apiKeys"
const val PREF_FIRST_KEY = "firstKey"
const val PREF_SECOND_KEY = "secondKey"

class EncryptedPreferencesImpl(context: Context) : EncryptedPreferences {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build() as? MasterKey

    private val preferences = masterKey?.let {
        EncryptedSharedPreferences.create(
            context,
            PREF_NAME,
            it,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun saveEncryptedData(keys: Keys): Boolean {
        return if (preferences != null) {
            preferences.edit {
                putString(PREF_FIRST_KEY, keys.firstKey)
                putString(PREF_SECOND_KEY, keys.secondKey)
            }
            true
        } else false
    }

    override fun readEncryptedData(): Keys? {
        val firstKey = preferences?.getString(PREF_FIRST_KEY, null)
        val secondKey = preferences?.getString(PREF_SECOND_KEY, null)

        return if (firstKey != null && secondKey != null)
            Keys(firstKey = firstKey, secondKey = secondKey)
        else null
    }

    override fun areApiKeysReady(): Boolean {
        val firstCondition = preferences != null
                && preferences.contains(PREF_FIRST_KEY)
                && preferences.contains(PREF_SECOND_KEY)
        val secondCondition = readEncryptedData() != null
        return firstCondition && secondCondition
    }
}