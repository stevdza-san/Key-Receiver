package com.stevdza_san.keyreceiverdemo.util

sealed class RequestState<out T> {
    data object Idle : RequestState<Nothing>()
    data object Loading : RequestState<Nothing>()
    data class Success<T>(val data: T) : RequestState<T>()
    data class Error(val message: String) : RequestState<Nothing>() {
        fun parseError(): String {
            return if (message.contains("failed to connect to")) "Failed to Connect to the Server"
            else if (message.contains("timeout")) "Connection Timeout. Please try again."
            else message
        }
    }

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isError() = this is Error
}