package com.example.libraryapp.ui.viewmodel

import com.example.libraryapp.data.model.Book

data class BookUiState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val errorMessage: String? = null
)