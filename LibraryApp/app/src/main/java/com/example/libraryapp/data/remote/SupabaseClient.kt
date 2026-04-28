package com.example.libraryapp.data.remote

import com.example.libraryapp.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClientProvider {

    // Bu client, Android uygulamamizi Supabase projemize baglar.
    // URL ve key bilgilerini local.properties icinden BuildConfig ile aliyoruz.
    // Boylece key'i direkt Kotlin kodunun icine yazmamis oluyoruz.

    val client = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_ANON_KEY
    ) {
        // Kullanici kayit, giris ve cikis islemleri icin
        install(Auth)

        // Supabase tablolarindan veri okumak ve veri eklemek icin
        install(Postgrest)
    }
}