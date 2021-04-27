plugins {
    id("com.android.library")
    kotlin("android")
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // Kotlin
    api(Dependencies.kotlinStdLib)
    api(Dependencies.ktxCore)
    api(Dependencies.kotlinCoroutines)
    // Support
    api(Dependencies.appCompat)
    api(Dependencies.preferences)
    // Logging
    api(Dependencies.timber)
    // Lifecycle
    api(Dependencies.lifecycle)
    // JWT token
    api(Dependencies.jwtToken)
    // Testing
    testImplementation(Dependencies.jUnit)
    testImplementation(Dependencies.jUnitTest)
    testImplementation(Dependencies.mockk)
    testImplementation(Dependencies.coroutinesTest)
}