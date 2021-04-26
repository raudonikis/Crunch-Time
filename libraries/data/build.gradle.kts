plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

dependencies {
    implementation(project(":libraries:common"))
    implementation(project(":libraries:navigation"))
    // DI
    implementation("com.google.dagger:hilt-android:2.33-beta")
    kapt("com.google.dagger:hilt-android-compiler:2.33-beta")
    // Room database
    api("androidx.room:room-runtime:2.2.6")
    api("androidx.room:room-ktx:2.2.6")
    kapt("androidx.room:room-compiler:2.2.6")
}