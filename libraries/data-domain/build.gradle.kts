plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

dependencies {
    implementation(project(":libraries:common"))
    implementation(project(":libraries:data"))
    api(project(":libraries:network"))
    // Serialization
    implementation("com.squareup.moshi:moshi-kotlin:1.11.0")
    // DI
    implementation("com.google.dagger:hilt-android:2.33-beta")
    kapt("com.google.dagger:hilt-android-compiler:2.33-beta")
    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}