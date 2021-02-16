plugins {
    id("com.android.library")
    kotlin("android")
}

dependencies {
    // Support
    api(Dependencies.constraintLayout)
    api(Dependencies.recyclerView)
    // Material
    api(Dependencies.material)
}