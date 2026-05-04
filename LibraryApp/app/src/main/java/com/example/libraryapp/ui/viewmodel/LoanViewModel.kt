package com.example.libraryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.data.repository.LoanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoanViewModel(
    private val loanRepository: LoanRepository = LoanRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoanUiState())
    val uiState: StateFlow<LoanUiState> = _uiState.asStateFlow()

    fun loadActiveBorrowedBookIds() {
        viewModelScope.launch {
            val result = loanRepository.getMyActiveBorrowedBookIds()

            result.onSuccess { ids ->
                _uiState.update {
                    it.copy(activeBorrowedBookIds = ids)
                }
            }
        }
    }

    fun borrowBook(bookId: Long, days: Int) {
        val safeDays = days.coerceIn(1, 5)

        if (_uiState.value.activeBorrowedBookIds.contains(bookId)) {
            _uiState.update {
                it.copy(
                    errorMessage = "You already borrowed this book. Please check My Books.",
                    successMessage = null
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    successMessage = null,
                    errorMessage = null
                )
            }

            val result = loanRepository.borrowBook(bookId, safeDays)

            result.onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        successMessage = "Book borrowed successfully for $safeDays day(s).",
                        errorMessage = null,
                        activeBorrowedBookIds = it.activeBorrowedBookIds + bookId
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        successMessage = null,
                        errorMessage = getFriendlyBorrowError(error.message)
                    )
                }
            }
        }
    }

    fun clearMessages() {
        _uiState.update {
            it.copy(
                successMessage = null,
                errorMessage = null
            )
        }
    }

    fun clearState() {
        _uiState.value = LoanUiState()
    }

    private fun getFriendlyBorrowError(message: String?): String {
        val errorText = message.orEmpty().lowercase()

        return when {
            "active borrow record" in errorText ->
                "You already borrowed this book. Please check My Books."

            "out of stock" in errorText ->
                "This book is out of stock."

            "duration" in errorText || "between 1 and 5" in errorText ->
                "Borrow duration must be between 1 and 5 days."

            "logged in" in errorText ->
                "Please login before borrowing a book."

            else ->
                "Borrowing failed. Please try again."
        }
    }

}