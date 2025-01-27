package com.stevdza_san.keyreceiverdemo.util

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.nio.charset.StandardCharsets
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import java.util.Base64
import javax.crypto.Cipher

const val KEYSTORE_ALIAS = "apiKeys"
const val ANDROID_KEYSTORE = "AndroidKeyStore"
const val TRANSFORMATION = "RSA/ECB/PKCS1Padding"

object KeyPairHandler {
    private val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply {
        load(null)
    }

    fun generateKeyPair() {
        if (keyStore != null && keyStore.containsAlias(KEYSTORE_ALIAS)) return

        val keyPairGenerator = KeyPairGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_RSA,
            ANDROID_KEYSTORE
        )

        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEYSTORE_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
            .setDigests(KeyProperties.DIGEST_SHA256)
            .setUserAuthenticationRequired(false)
            .build()

        keyPairGenerator.initialize(keyGenParameterSpec)
        keyPairGenerator.generateKeyPair()
    }

    fun getPublicKeyString(): String {
        val privateKeyEntry = keyStore?.getEntry(KEYSTORE_ALIAS, null) as? KeyStore.PrivateKeyEntry
        val encodedPublicKey = privateKeyEntry?.certificate?.publicKey?.encoded
        return Base64.getEncoder().encodeToString(encodedPublicKey)
    }

    fun decryptTheData(encryptedData: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey())
        val decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData))
        return String(decryptedData, StandardCharsets.UTF_8)
    }

    fun encrypt(bytes: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey())
        val encrypted = cipher.doFinal(bytes)
        return encrypted
    }

    fun decrypt(bytes: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey())
        return cipher.doFinal(bytes)
    }

    private fun getPrivateKey(): PrivateKey? {
        val privateKeyEntry = keyStore.getEntry(KEYSTORE_ALIAS, null) as? KeyStore.PrivateKeyEntry
        return privateKeyEntry?.privateKey
    }

    private fun getPublicKey(): PublicKey? {
        val privateKeyEntry = keyStore.getEntry(KEYSTORE_ALIAS, null) as? KeyStore.PrivateKeyEntry
        return privateKeyEntry?.certificate?.publicKey
    }

}