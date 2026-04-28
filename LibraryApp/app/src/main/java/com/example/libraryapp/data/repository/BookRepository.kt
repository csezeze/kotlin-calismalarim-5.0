package com.example.libraryapp.data.repository

import com.example.libraryapp.data.model.Book
import com.example.libraryapp.data.remote.SupabaseClientProvider
import io.github.jan.supabase.postgrest.from

class BookRepository {

    private val client = SupabaseClientProvider.client

    // Supabase books tablosundaki tum kitaplari getirir.
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
}