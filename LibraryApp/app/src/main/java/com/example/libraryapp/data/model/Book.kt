package com.example.libraryapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: Long = 0,
    val title: String = "",
    val author: String = "",
    val category: String? = null,
    val description: String? = null,

    @SerialName("image_url")
    val imageUrl: String? = null,

    @SerialName("available_count")
    val availableCount: Int = 0,

    @SerialName("created_at")
    val createdAt: String? = null
)