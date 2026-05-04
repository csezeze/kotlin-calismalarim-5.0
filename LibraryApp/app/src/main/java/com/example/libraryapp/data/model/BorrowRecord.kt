package com.example.libraryapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BorrowRecord(
    val id: Long = 0,

    @SerialName("user_id")
    val userId: String = "",

    @SerialName("book_id")
    val bookId: Long = 0,

    @SerialName("borrowed_at")
    val borrowedAt: String? = null,

    @SerialName("due_at")
    val dueAt: String? = null,

    @SerialName("returned_at")
    val returnedAt: String? = null,

    val status: String = "active",

    @SerialName("created_at")
    val createdAt: String? = null
)