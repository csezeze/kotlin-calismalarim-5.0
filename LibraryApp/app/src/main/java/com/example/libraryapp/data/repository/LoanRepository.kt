package com.example.libraryapp.data.repository

import com.example.libraryapp.data.model.Loan
import com.example.libraryapp.data.remote.SupabaseClientProvider
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/**
 * borrow_records tablosundan aktif kiralanan kitapları okur.
 * Kullanıcı bu kitabı zaten aldıysa UI tarafında butonu kapatır.
 */
class LoanRepository {

    private val client = SupabaseClientProvider.client

    suspend fun borrowBook(bookId: Long, days: Int): Result<Unit> {
        return try {
            client.postgrest.rpc(
                function = "borrow_book_with_record",
                parameters = buildJsonObject {
                    put("p_book_id", bookId)
                    put("p_days", days)
                }
            )

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMyLoans(): Result<List<Loan>> {
        return try {
            val loans = client
                .from("borrow_records")
                .select()
                .decodeList<Loan>()

            Result.success(loans)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMyActiveBorrowedBookIds(): Result<Set<Long>> {
        return try {
            val activeLoans = client
                .from("borrow_records")
                .select {
                    filter {
                        eq("status", "active")
                    }
                }
                .decodeList<Loan>()

            val activeBookIds = activeLoans
                .filter { it.returnedAt == null }
                .map { it.bookId }
                .toSet()

            Result.success(activeBookIds)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}