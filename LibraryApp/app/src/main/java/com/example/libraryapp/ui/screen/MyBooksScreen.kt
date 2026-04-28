package com.example.libraryapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.libraryapp.data.model.BorrowedBook
import com.example.libraryapp.ui.viewmodel.MyBooksViewModel

@Composable
fun MyBooksScreen(
    myBooksViewModel: MyBooksViewModel,
    onBackClick: () -> Unit
) {
    val uiState by myBooksViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        myBooksViewModel.loadMyBooks()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF07111F))
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                OutlinedButton(
                    onClick = onBackClick
                ) {
                    Text("Back")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "My Borrowed Books",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFB8F7D4)
                )

                Text(
                    text = "${uiState.borrowedBooks.size} borrowed books",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFB8C7D9)
                )
            }

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFFB8F7D4)
                        )
                    }
                }

                uiState.errorMessage != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = uiState.errorMessage ?: "Unknown error.",
                            color = Color(0xFFFF9E9E)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                myBooksViewModel.loadMyBooks()
                            }
                        ) {
                            Text("Try Again")
                        }
                    }
                }

                uiState.borrowedBooks.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "You have not borrowed any books yet.",
                            color = Color(0xFFB8C7D9)
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 24.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(uiState.borrowedBooks) { borrowedBook ->
                            BorrowedBookCard(borrowedBook = borrowedBook)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BorrowedBookCard(
    borrowedBook: BorrowedBook
) {
    val book = borrowedBook.book
    val loan = borrowedBook.loan

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF101C2E)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            AssistChip(
                onClick = {},
                label = {
                    Text(
                        text = loan.status,
                        color = Color(0xFFB8F7D4)
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color(0xFF18283D),
                    labelColor = Color(0xFFB8F7D4)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = book.author,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFB8C7D9)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Borrowed at: ${loan.borrowedAt ?: "Unknown"}",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFCFD8E6)
            )
        }
    }
}