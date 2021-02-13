plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

dependencies {
    implementation(project(Modules.Libraries.common))
    implementation(project(Modules.Libraries.data))
    // Networking
    implementation(Dependencies.retrofit)
    implementation(Dependencies.moshi)
    implementation(Dependencies.moshiConverter)
    implementation(Dependencies.scalarsConverter)
    implementation(Dependencies.okHttp)
    // Hilt
    implementation(Dependencies.hilt)
    kapt(Dependencies.hiltCompiler)
}