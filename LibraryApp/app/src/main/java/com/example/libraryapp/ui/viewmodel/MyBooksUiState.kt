package com.example.libraryapp.ui.viewmodel

import com.example.libraryapp.data.model.BorrowedBook

data class MyBooksUiState(
    val isLoading: Boolean = false,
    val borrowedBooks: List<BorrowedBook> = emptyList(),
    val errorMessage: String? = null
)