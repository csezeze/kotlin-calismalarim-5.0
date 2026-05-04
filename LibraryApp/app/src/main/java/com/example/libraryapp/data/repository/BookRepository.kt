package com.example.libraryapp.data.repository
import java.util.Locale
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
            val allBooksResult = getBooks()

            if (allBooksResult.isFailure) {
                return Result.failure(
                    allBooksResult.exceptionOrNull() ?: Exception("Books could not be loaded.")
                )
            }

            val allBooks = allBooksResult.getOrDefault(emptyList())
            val normalizedQuery = normalizeSearchText(query.trim())

            if (normalizedQuery.isEmpty()) {
                return Result.success(allBooks)
            }

            val filteredBooks = allBooks.filter { book ->
                val title = normalizeSearchText(book.title)
                val author = normalizeSearchText(book.author)
                val category = normalizeSearchText(book.category ?: "")
                val description = normalizeSearchText(book.description ?: "")

                title.contains(normalizedQuery) ||
                        author.contains(normalizedQuery) ||
                        category.contains(normalizedQuery) ||
                        description.contains(normalizedQuery)
            }

            Result.success(filteredBooks)
        } catch (e: Exception) {
            Result.failure(e)
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