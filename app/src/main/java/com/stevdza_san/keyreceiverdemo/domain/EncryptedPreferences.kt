package com.stevdza_san.keyreceiverdemo.domain

import com.stevdza_san.keyreceiverdemo.model.Keys

interface EncryptedPreferences {
    suspend fun saveEncryptedData(keys: Keys): Boolean
    suspend fun readEncryptedData(): Keys?
    suspend fun areApiKeysReady(): Boolean
}