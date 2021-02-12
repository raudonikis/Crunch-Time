plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    buildFeatures.viewBinding = true
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // Kotlin
    api(Dependencies.kotlinStdLib)
    api(Dependencies.ktxCore)
    api(Dependencies.kotlinCoroutines)
    // Support
    api(Dependencies.appCompat)
    // AndroidX
    api(Dependencies.constraintLayout)
    api(Dependencies.recyclerView)
    api(Dependencies.preferences)
    // Logging
    api(Dependencies.timber)
}