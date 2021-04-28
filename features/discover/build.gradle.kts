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
    implementation(project(Modules.Libraries.common))
    implementation(project(Modules.Libraries.commonUi))
    implementation(project(Modules.Libraries.navigation))
    implementation(project(Modules.Libraries.dataDomain))
    implementation(project(Modules.Features.details))
    // Hilt
    implementation(Dependencies.hilt)
    implementation(Dependencies.hiltLifecycle)
    kapt(Dependencies.hiltCompiler)
    kapt(Dependencies.hiltLifecycleCompiler)
    // Images
    kapt(Dependencies.glideAnnotation)
    // Testing
    testImplementation(project(Modules.Libraries.commonTesting))
    testImplementation(Dependencies.jUnit)
    testImplementation(Dependencies.jUnitTest)
    testImplementation(Dependencies.mockk)
    testImplementation(Dependencies.coroutinesTest)
    testImplementation(Dependencies.turbine)
}