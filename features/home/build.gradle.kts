plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    buildFeatures.viewBinding = true
}

dependencies {
    implementation(project(":libraries:common"))
    implementation(project(":libraries:common-ui"))
    implementation(project(":libraries:data-domain"))
    implementation(project(":libraries:navigation"))
    // Hilt
    implementation("com.google.dagger:hilt-android:2.33-beta")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("com.google.dagger:hilt-android-compiler:2.33-beta")
    kapt("androidx.hilt:hilt-compiler:1.0.0-beta01")
}