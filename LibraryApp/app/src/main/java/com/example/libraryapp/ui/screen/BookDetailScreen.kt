package com.example.libraryapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.libraryapp.data.model.Book
import com.example.libraryapp.ui.viewmodel.LoanViewModel
import androidx.compose.runtime.LaunchedEffect

@Composable
fun BookDetailScreen(
    book: Book?,
    loanViewModel: LoanViewModel,
    onBorrowSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    val loanUiState by loanViewModel.uiState.collectAsState()

    LaunchedEffect(loanUiState.successMessage) {
        if (loanUiState.successMessage != null) {
            onBorrowSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF07111F))
            .statusBarsPadding()
            .padding(20.dp)
    ) {
        if (book == null) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Book not found.",
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(onClick = onBackClick) {
                    Text("Go Back")
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                OutlinedButton(
                    onClick = onBackClick
                ) {
                    Text("Back")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF101C2E)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(22.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AssistChip(
                            onClick = {},
                            label = {
                                Text(
                                    text = book.category ?: "General",
                                    color = Color(0xFFB8F7D4)
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Color(0xFF18283D),
                                labelColor = Color(0xFFB8F7D4)
                            )
                        )

                        Text(
                            text = book.title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB8F7D4)
                        )

                        Text(
                            text = "Author: ${book.author}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFB8C7D9)
                        )

                        Text(
                            text = book.description ?: "No description.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFCFD8E6)
                        )

                        Text(
                            text = "Available copies: ${book.availableCount}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = if (book.availableCount > 0) {
                                Color(0xFFB8F7D4)
                            } else {
                                Color(0xFFFF9E9E)
                            }
                        )

                        if (loanUiState.successMessage != null) {
                            Text(
                                text = loanUiState.successMessage ?: "",
                                color = Color(0xFFB8F7D4),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        if (loanUiState.errorMessage != null) {
                            Text(
                                text = loanUiState.errorMessage ?: "",
                                color = Color(0xFFFF9E9E),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                loanViewModel.borrowBook(book.id)
                            },
                            enabled = !loanUiState.isLoading &&
                                    book.availableCount > 0 &&
                                    loanUiState.successMessage == null,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            if (loanUiState.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.height(20.dp),
                                    strokeWidth = 2.dp,
                                    color = Color.White
                                )
                            } else {
                                Text("Borrow Book")
                            }
                        }
                    }
                }
            }
        }
    }
}