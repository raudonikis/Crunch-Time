plugins {
    id("com.android.library")
    kotlin("android")
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // Kotlin
    api("org.jetbrains.kotlin:kotlin-stdlib:1.4.31")
    api("androidx.core:core-ktx:1.3.2")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")
    // Support
    api("androidx.appcompat:appcompat:1.2.0")
    api("androidx.preference:preference:1.1.1")
    // Logging
    api("com.jakewharton.timber:timber:4.7.1")
    // Lifecycle
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    // JWT token
    api("com.auth0.android:jwtdecode:2.0.0")
}