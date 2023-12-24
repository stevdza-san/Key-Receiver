package com.stevdza_san.keyreceiverdemo.model

import kotlinx.serialization.Serializable

@Serializable
data class Keys(
    val firstKey: String,
    val secondKey: String
)
