plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("com.github.ben-manes.versions") version "0.36.0"
    kotlin("android")
    kotlin("kapt")
}

apply {
    from("../projectDependencyGraph.gradle")
}

android {

    defaultConfig {
        applicationId = Releases.applicationId
        versionCode = Releases.versionCode
        versionName = Releases.versionName
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    hilt {
        enableExperimentalClasspathAggregation = true
    }
    lintOptions {
        isCheckReleaseBuilds = false
    }
    buildFeatures.viewBinding = true
}

dependencies {
    implementation(project(Modules.Libraries.common))
    implementation(project(Modules.Libraries.core))
    implementation(project(Modules.Libraries.commonUi))
    implementation(project(Modules.Libraries.navigation))
    implementation(project(Modules.Libraries.data))
    implementation(project(Modules.Features.bottomNavigation))
    implementation(project(Modules.Features.login))
    implementation(project(Modules.Features.settings))
    // Hilt
    implementation(Dependencies.hilt)
    implementation(Dependencies.hiltLifecycle)
    kapt(Dependencies.hiltCompiler)
    kapt(Dependencies.hiltLifecycleCompiler)
    // Testing
    testImplementation(Dependencies.jUnit)
    androidTestImplementation(Dependencies.jUnitTest)
    androidTestImplementation(Dependencies.espresso)
    // Quality
//    debugImplementation(Dependencies.leakCanary)
}