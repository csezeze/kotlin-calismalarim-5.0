package com.example.libraryapp.data.repository

import com.example.libraryapp.data.model.Loan
import com.example.libraryapp.data.remote.SupabaseClientProvider
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class LoanRepository {

    private val client = SupabaseClientProvider.client

    // Artik direkt loans tablosuna insert atmiyoruz.
    // Supabase tarafindaki guvenli borrow_book_safe function'ini cagiriyoruz.
    suspend fun borrowBook(bookId: Long): Result<Unit> {
        return try {
            client.postgrest.rpc(
                function = "borrow_book_safe",
                parameters = buildJsonObject {
                    put("p_book_id", bookId)
                }
            )

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Giris yapmis kullanicinin odunc aldigi kitap kayitlarini getirir.
    suspend fun getMyLoans(): Result<List<Loan>> {
        return try {
            val loans = client
                .from("loans")
                .select()
                .decodeList<Loan>()

            Result.success(loans)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}