package com.example.libraryapp.ui.screen

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.libraryapp.data.model.Book
import com.example.libraryapp.ui.component.BookCard
import com.example.libraryapp.ui.viewmodel.BookViewModel
import com.example.libraryapp.ui.viewmodel.LoanViewModel
import java.util.Locale

@Composable
fun BookListScreen(
    bookViewModel: BookViewModel,
    loanViewModel: LoanViewModel,
    onBookClick: (Long) -> Unit,
    onMyBooksClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onBorrowSuccess: () -> Unit
) {
    val bookUiState by bookViewModel.uiState.collectAsState()
    val loanUiState by loanViewModel.uiState.collectAsState()

    var searchText by remember { mutableStateOf("") }
    var selectedBook by remember { mutableStateOf<Book?>(null) }
    var selectedDays by remember { mutableStateOf(1) }

    val searchedBooks = bookUiState.books.filter { book ->
        if (searchText.trim().isEmpty()) {
            true
        } else {
            val search = normalizeSearchText(searchText)
            val title = normalizeSearchText(book.title)
            val author = normalizeSearchText(book.author)
            val category = normalizeSearchText(book.category ?: "")
            val description = normalizeSearchText(book.description ?: "")

            title.contains(search) ||
                    author.contains(search) ||
                    category.contains(search) ||
                    description.contains(search)
        }
    }

    LaunchedEffect(Unit) {
        loanViewModel.clearMessages()
        loanViewModel.loadActiveBorrowedBookIds()
    }

    LaunchedEffect(loanUiState.successMessage) {
        if (loanUiState.successMessage != null) {
            onBorrowSuccess()
            loanViewModel.loadActiveBorrowedBookIds()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF07111F))
    ) {
        when {
            bookUiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFFB8F7D4)
                )
            }

            bookUiState.errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = bookUiState.errorMessage ?: "Unknown error.",
                        color = Color(0xFFFF9E9E)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            bookViewModel.loadBooks()
                        }
                    ) {
                        Text("Try Again")
                    }
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Library Books",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB8F7D4)
                            )

                            Text(
                                text = if (searchText.trim().isEmpty()) {
                                    "${bookUiState.books.size} books"
                                } else {
                                    "${searchedBooks.size} result(s)"
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFB8C7D9)
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = onMyBooksClick
                            ) {
                                Text("My Books")
                            }

                            OutlinedButton(
                                onClick = onLogoutClick
                            ) {
                                Text("Logout")
                            }
                        }
                    }

                    OutlinedTextField(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                        },
                        label = {
                            Text("Search book or author")
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White,
                            focusedBorderColor = Color(0xFFB8F7D4),
                            unfocusedBorderColor = Color(0xFF6F7D91),
                            focusedLabelColor = Color(0xFFB8F7D4),
                            unfocusedLabelColor = Color(0xFFB8C7D9)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    if (loanUiState.successMessage != null) {
                        Text(
                            text = loanUiState.successMessage ?: "",
                            modifier = Modifier.padding(horizontal = 20.dp),
                            color = Color(0xFFB8F7D4),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    if (loanUiState.errorMessage != null) {
                        Text(
                            text = loanUiState.errorMessage ?: "",
                            modifier = Modifier.padding(horizontal = 20.dp),
                            color = Color(0xFFFF9E9E),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    if (searchedBooks.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "No books found.",
                                color = Color(0xFFB8C7D9),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                start = 20.dp,
                                end = 20.dp,
                                bottom = 24.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            items(searchedBooks) { book ->
                                val alreadyBorrowed =
                                    loanUiState.activeBorrowedBookIds.contains(book.id)

                                BookCard(
                                    book = book,
                                    alreadyBorrowed = alreadyBorrowed,
                                    onCardClick = {
                                        onBookClick(book.id)
                                    },
                                    onBorrowClick = {
                                        selectedDays = 1
                                        selectedBook = book
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (selectedBook != null) {
            AlertDialog(
                onDismissRequest = {
                    selectedBook = null
                },
                title = {
                    Text("Borrow Book")
                },
                text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Choose borrowing duration. Maximum is 5 days.")

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            for (day in 1..5) {
                                OutlinedButton(
                                    onClick = {
                                        selectedDays = day
                                    }
                                ) {
                                    Text(
                                        text = if (selectedDays == day) {
                                            "$day ✓"
                                        } else {
                                            "$day"
                                        }
                                    )
                                }
                            }
                        }

                        Text(
                            text = "Selected: $selectedDays day(s)",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val book = selectedBook

                            if (book != null) {
                                loanViewModel.borrowBook(
                                    bookId = book.id,
                                    days = selectedDays
                                )
                            }

                            selectedBook = null
                        },
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            selectedBook = null
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

private fun normalizeSearchText(text: String): String {
    var result = text.lowercase(Locale("tr", "TR"))

    result = result.replace("ç", "c")
    result = result.replace("ğ", "g")
    result = result.replace("ı", "i")
    result = result.replace("ö", "o")
    result = result.replace("ş", "s")
    result = result.replace("ü", "u")

    return result
}