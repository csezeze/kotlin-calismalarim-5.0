package com.example.libraryapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: String = "",

    @SerialName("full_name")
    val fullName: String? = null,

    val email: String? = null,

    @SerialName("created_at")
    val createdAt: String? = null
)