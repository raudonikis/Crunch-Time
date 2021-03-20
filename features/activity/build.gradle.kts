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
    implementation(project(Modules.Libraries.common))
    implementation(project(Modules.Libraries.commonUi))
    implementation(project(Modules.Libraries.navigation))
    implementation(project(Modules.Libraries.dataDomain))
    // Hilt
    implementation(Dependencies.hilt)
    implementation(Dependencies.hiltLifecycle)
    kapt(Dependencies.hiltCompiler)
    kapt(Dependencies.hiltLifecycleCompiler)
}