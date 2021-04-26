plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    buildFeatures.viewBinding = true
}

dependencies {
    // Projects
    implementation(project(":libraries:common"))
    implementation(project(":libraries:data-domain"))
    // Support
    api("androidx.constraintlayout:constraintlayout:2.0.4")
    api("androidx.recyclerview:recyclerview:1.2.0")
    api("com.github.wada811:ViewBinding-ktx:1.1.1")
    // Material
    api("com.google.android.material:material:1.3.0")
    // UI
    api("com.github.bumptech.glide:glide:4.12.0")
    api("com.mikepenz:fastadapter:5.3.5")
    api("com.mikepenz:fastadapter-extensions-binding:5.3.5")
    api("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    api("com.github.chrisbanes:PhotoView:2.3.0")
    // Animations
    api("com.airbnb.android:lottie:3.6.1")
}