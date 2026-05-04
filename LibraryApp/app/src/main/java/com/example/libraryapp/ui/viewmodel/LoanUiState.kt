package com.example.libraryapp.ui.viewmodel

data class LoanUiState(
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val activeBorrowedBookIds: Set<Long> = emptySet()
)