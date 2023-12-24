package com.stevdza_san.keyreceiverdemo.domain

import com.stevdza_san.keyreceiverdemo.model.Keys

interface EncryptedPreferences {
    fun saveEncryptedData(keys: Keys): Boolean
    fun readEncryptedData(): Keys?
    fun areApiKeysReady(): Boolean
}