plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    buildFeatures.viewBinding = true
}

dependencies {
    // Support
    api(Dependencies.constraintLayout)
    api(Dependencies.recyclerView)
    // Material
    api(Dependencies.material)
}