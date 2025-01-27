package com.stevdza_san.keyreceiverdemo.data

import android.content.Context
import androidx.datastore.dataStore
import com.stevdza_san.keyreceiverdemo.domain.EncryptedPreferences
import com.stevdza_san.keyreceiverdemo.model.Keys
import com.stevdza_san.keyreceiverdemo.model.KeysSerializer
import kotlinx.coroutines.flow.firstOrNull

const val PREF_NAME = "apiKeys"
//const val PREF_FIRST_KEY = "firstKey"
//const val PREF_SECOND_KEY = "secondKey"

private val Context.dataStore by dataStore(
    fileName = PREF_NAME,
    serializer = KeysSerializer
)

class EncryptedPreferencesImpl(context: Context) : EncryptedPreferences {
//    private val masterKey = MasterKey.Builder(context)
//        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
//        .build() as? MasterKey
//
//    private val preferences = masterKey?.let {
//        EncryptedSharedPreferences.create(
//            context,
//            PREF_NAME,
//            it,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )
//    }

//    override fun saveEncryptedData(keys: Keys): Boolean {
//        return if (preferences != null) {
//            preferences.edit {
//                putString(PREF_FIRST_KEY, keys.firstKey)
//                putString(PREF_SECOND_KEY, keys.secondKey)
//            }
//            true
//        } else false
//    }
//
//    override fun readEncryptedData(): Keys? {
//        val firstKey = preferences?.getString(PREF_FIRST_KEY, null)
//        val secondKey = preferences?.getString(PREF_SECOND_KEY, null)
//
//        return if (firstKey != null && secondKey != null)
//            Keys(firstKey = firstKey, secondKey = secondKey)
//        else null
//    }
//
//    override fun areApiKeysReady(): Boolean {
//        val firstCondition = preferences != null
//                && preferences.contains(PREF_FIRST_KEY)
//                && preferences.contains(PREF_SECOND_KEY)
//        val secondCondition = readEncryptedData() != null
//        return firstCondition && secondCondition
//    }

    private val dataStore = context.dataStore

    override suspend fun saveEncryptedData(keys: Keys): Boolean {
        return try {
            dataStore.updateData { keys }
            true
        } catch (e: Exception) {
            println("Error during saveEncryptedData: ${e.message}")
            false
        }
    }

    override suspend fun readEncryptedData(): Keys? {
        return dataStore.data.firstOrNull()
    }

    override suspend fun areApiKeysReady(): Boolean {
        return try {
            val keys = readEncryptedData()
            keys != null && keys.firstKey.isNotEmpty() && keys.secondKey.isNotEmpty()
        } catch (e: Exception) {
            println("Error during areApiKeysReady: ${e.message}")
            false
        }
    }
}