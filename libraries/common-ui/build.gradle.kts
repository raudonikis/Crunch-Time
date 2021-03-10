plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    buildFeatures.viewBinding = true
}

dependencies {
    // Projects
    implementation(project(Modules.Libraries.common))
    // Support
    api(Dependencies.constraintLayout)
    api(Dependencies.recyclerView)
    api(Dependencies.viewBindingKtx)
    // Material
    api(Dependencies.material)
    // Images
    api(Dependencies.glide)
}