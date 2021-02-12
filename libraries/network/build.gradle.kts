plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

dependencies {
    implementation(project(Modules.Libraries.common))
    api(project(Modules.Libraries.data))
    // Networking
    api(Dependencies.retrofit)
    api(Dependencies.moshi)
    // Hilt
    implementation(Dependencies.hilt)
    kapt(Dependencies.hiltCompiler)
}