plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

dependencies {
    implementation(project(Modules.Libraries.common))
    implementation(project(Modules.Libraries.core))
    implementation(project(Modules.Libraries.data))
    api(project(Modules.Libraries.network))
    // Serialization
    implementation(Dependencies.moshi)
    // DI
    implementation(Dependencies.hilt)
    kapt(Dependencies.hiltCompiler)
    // Testing
    testImplementation(Dependencies.jUnit)
    testImplementation(Dependencies.jUnitTest)
    testImplementation(Dependencies.mockk)
    testImplementation(Dependencies.coroutinesTest)
}