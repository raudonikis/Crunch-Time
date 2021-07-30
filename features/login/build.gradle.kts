plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    buildFeatures {
        viewBinding = true
        // Enables Jetpack Compose for this module
        compose = true
    }
    composeOptions {
        kotlinCompilerVersion = "1.5.10"
        kotlinCompilerExtensionVersion = Dependencies.compose_version
    }
}

dependencies {
    implementation(project(Modules.Libraries.common))
    implementation(project(Modules.Libraries.core))
    implementation(project(Modules.Libraries.commonUi))
    implementation(project(Modules.Libraries.navigation))
    implementation(project(Modules.Libraries.data))
    implementation(project(Modules.Libraries.dataDomain))
    // Hilt
    implementation(Dependencies.hilt)
    implementation(Dependencies.hiltLifecycle)
    kapt(Dependencies.hiltCompiler)
    kapt(Dependencies.hiltLifecycleCompiler)
    // Compose
    implementation(Dependencies.compose)
    implementation(Dependencies.composeFoundation)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.composeMaterialIconsCore)
    implementation(Dependencies.composeMaterialIconsExtended)
    implementation(Dependencies.composeTooling)
    implementation(Dependencies.composeLivedata)
    // Testing
    testImplementation(project(Modules.Libraries.commonTesting))
    testImplementation(Dependencies.jUnit)
    testImplementation(Dependencies.jUnitTest)
    testImplementation(Dependencies.mockk)
    testImplementation(Dependencies.coroutinesTest)
    testImplementation(Dependencies.turbine)
    // UI Testing
    androidTestImplementation(Dependencies.composeUiTest)
}