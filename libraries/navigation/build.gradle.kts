plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

dependencies {
    implementation(project(":libraries:common"))
    // Navigation
    api("androidx.navigation:navigation-fragment-ktx:2.3.5")
    api("androidx.navigation:navigation-ui-ktx:2.3.5")
    // Hilt
    implementation("com.google.dagger:hilt-android:2.33-beta")
    kapt("com.google.dagger:hilt-android-compiler:2.33-beta")
}