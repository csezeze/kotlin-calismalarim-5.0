package com.example.libraryapp.data.repository

import com.example.libraryapp.data.model.Book
import com.example.libraryapp.data.remote.SupabaseClientProvider
import io.github.jan.supabase.postgrest.from

class BookRepository {

    private val client = SupabaseClientProvider.client

    // Gets all books from the books table.
    suspend fun getBooks(): Result<List<Book>> {
        return try {
            val books = client
                .from("books")
                .select()
                .decodeList<Book>()

            Result.success(books)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Homework requirement: search books.
    suspend fun searchBooks(query: String): Result<List<Book>> {
        return try {
            val trimmedQuery = query.trim()

            if (trimmedQuery.isEmpty()) {
                return getBooks()
            }

            val books = client
                .from("books")
                .select {
                    filter {
                        ilike("title", "%$trimmedQuery%")
                    }
                }
                .decodeList<Book>()

            Result.success(books)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Homework requirement: update book.
    suspend fun updateBook(book: Book): Result<Unit> {
        return try {
            client
                .from("books")
                .update(
                    {
                        set("title", book.title)
                        set("author", book.author)
                        set("category", book.category)
                        set("description", book.description)
                        set("image_url", book.imageUrl)
                        set("available_count", book.availableCount)
                    }
                ) {
                    filter {
                        eq("id", book.id)
                    }
                }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Homework requirement: delete book.
    suspend fun deleteBook(bookId: Long): Result<Unit> {
        return try {
            client
                .from("books")
                .delete {
                    filter {
                        eq("id", bookId)
                    }
                }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}