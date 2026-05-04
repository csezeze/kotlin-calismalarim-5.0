package com.example.libraryapp.data.repository

import com.example.libraryapp.data.model.BorrowRecord
import com.example.libraryapp.data.remote.SupabaseClientProvider
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class BorrowRecordRepository {

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

    suspend fun getMyBorrowRecords(): Result<List<BorrowRecord>> {
        return try {
            val records = client
                .from("borrow_records")
                .select()
                .decodeList<BorrowRecord>()

            Result.success(records)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}