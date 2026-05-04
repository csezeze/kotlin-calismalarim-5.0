package com.example.libraryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.data.model.BorrowedBook
import com.example.libraryapp.data.repository.BookRepository
import com.example.libraryapp.data.repository.LoanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyBooksViewModel(
    private val loanRepository: LoanRepository = LoanRepository(),
    private val bookRepository: BookRepository = BookRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyBooksUiState())
    val uiState: StateFlow<MyBooksUiState> = _uiState.asStateFlow()

    fun loadMyBooks() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    //logout sonrası eski kullanıcının kitapları görünmesin
                    borrowedBooks = emptyList(),
                    errorMessage = null
                )
            }

            val loansResult = loanRepository.getMyLoans()
            val booksResult = bookRepository.getBooks()

            if (loansResult.isSuccess && booksResult.isSuccess) {
                val loans = loansResult.getOrDefault(emptyList())
                val books = booksResult.getOrDefault(emptyList())

                val borrowedBooks = loans.mapNotNull { loan ->
                    val book = books.find { it.id == loan.bookId }

                    if (book != null) {
                        BorrowedBook(
                            loan = loan,
                            book = book
                        )
                    } else {
                        null
                    }
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        borrowedBooks = borrowedBooks,
                        errorMessage = null
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Borrowed books could not be loaded."
                    )
                }
            }
        }
    }
    fun clearMyBooks() {
        _uiState.value = MyBooksUiState()
    }
}