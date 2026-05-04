package com.example.libraryapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.libraryapp.data.model.Book

@Composable
fun BookCard(
    book: Book,
    alreadyBorrowed: Boolean,
    onCardClick: () -> Unit,
    onBorrowClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCardClick()
            },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF101C2E)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
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
                }

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
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = book.description ?: "No description.",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFCFD8E6)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Available copies: ${book.availableCount}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = if (book.availableCount > 0) {
                    Color(0xFFB8F7D4)
                } else {
                    Color(0xFFFF9E9E)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (alreadyBorrowed) {
                Text(
                    text = "Already Borrowed",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFD166)
                )
            } else if (book.availableCount > 0) {
                Button(
                    onClick = {
                        onBorrowClick()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Borrow Book")
                }
            } else {
                Text(
                    text = "Out of Stock",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF9E9E)
                )
            }
        }
    }
}