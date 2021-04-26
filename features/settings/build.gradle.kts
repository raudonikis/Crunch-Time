plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    buildFeatures.viewBinding = true
}

dependencies {
    implementation(project(":libraries:common"))
    implementation(project(":libraries:common-ui"))
    implementation(project(":libraries:navigation"))
    implementation(project(":libraries:data-domain"))
    // Hilt
    implementation("com.google.dagger:hilt-android:2.33-beta")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0-beta01")
    kapt("com.google.dagger:hilt-android-compiler:2.33-beta")
    kapt("androidx.hilt:hilt-compiler:1.0.0-beta01")
}