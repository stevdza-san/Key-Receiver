package com.stevdza_san.keyreceiverdemo.domain

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface KeyProviderService {
    @Headers("Content-Type: application/json")
    @POST("/api/provide")
    suspend fun getEncryptedApiKeys(@Body publicKey: String): Response<String>
}