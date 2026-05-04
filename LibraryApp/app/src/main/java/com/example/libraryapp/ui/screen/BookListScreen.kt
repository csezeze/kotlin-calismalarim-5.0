package com.example.libraryapp.ui.screen

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

    var selectedBook by remember { mutableStateOf<Book?>(null) }
    var selectedDays by remember { mutableStateOf(1) }

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
                                text = "${bookUiState.books.size} books",
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

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 24.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(bookUiState.books) { book ->
                            val alreadyBorrowed = loanUiState.activeBorrowedBookIds.contains(book.id)

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