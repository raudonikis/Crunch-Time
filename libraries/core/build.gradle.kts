plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

dependencies {
    implementation(project(Modules.Libraries.common))
    // Hilt
    implementation(Dependencies.hilt)
    kapt(Dependencies.hiltCompiler)
}