import java.util.Properties
import java.io.FileInputStream
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")

if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

android {
    namespace = "com.example.libraryapp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.libraryapp"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        buildConfigField(
            "String",
            "SUPABASE_URL",
            "\"${localProperties.getProperty("SUPABASE_URL")}\""
        )

        buildConfigField(
            "String",
            "SUPABASE_ANON_KEY",
            "\"${localProperties.getProperty("SUPABASE_ANON_KEY")}\""
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    // Navigation Compose
    // Ekranlar arasi gecis
    implementation("androidx.navigation:navigation-compose:2.9.8")

    // ViewModel Compose
    // Login, register ve kitap ekranlarinda state yonetimi
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")

    // Kotlin Serialization
    // Supabase'den gelen JSON verilerini Kotlin data class'lara cevirmek icin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

    // Supabase BOM
    // Supabase kutuphanelerinin ayni surum ailesinden gelmesini saglar.
    implementation(platform("io.github.jan-tennert.supabase:bom:3.5.0"))

    // Supabase Auth
    // Register, login ve logout islemleri icin
    implementation("io.github.jan-tennert.supabase:auth-kt")

    // Supabase PostgREST
    // books, profiles ve loans tablolarina erismek icin
    implementation("io.github.jan-tennert.supabase:postgrest-kt")

    // Ktor Android Client Engine
    // Supabase'in internet uzerinden istek atabilmesi icin gerekli.
    implementation("io.ktor:ktor-client-android:3.4.3")
}