package com.example.libraryapp.data.repository


import com.example.libraryapp.data.model.UserProfile
import com.example.libraryapp.data.remote.SupabaseClientProvider
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.auth.auth

class AuthRepository {

    private val client = SupabaseClientProvider.client

    // Kullanici kayit islemi.
    // Once Supabase Auth tarafina email ve sifre ile kullanici ekliyoruz.
    // Sonra profiles tablosuna ad soyad ve email bilgisini ekliyoruz.
    suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }

            val user = client.auth.currentSessionOrNull()?.user
                ?: throw Exception("Kullanici olusturuldu ama oturum bilgisi alinamadi.")

            val profile = UserProfile(
                id = user.id.toString(),
                fullName = fullName,
                email = email
            )

            client.from("profiles").insert(profile)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Kullanici giris islemi.
    // Supabase Auth tarafinda email ve sifre dogruysa oturum acilir.
    suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Kullanici cikis islemi.
    suspend fun logout(): Result<Unit> {
        return try {
            client.auth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Su an giris yapmis kullanicinin id bilgisini verir.
    fun getCurrentUserId(): String? {
        return client.auth.currentSessionOrNull()?.user?.id.toString()
    }

    fun getCurrentUserEmail(): String? {
        return client.auth.currentSessionOrNull()?.user?.email
    }
}