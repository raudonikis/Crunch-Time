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
        applicationId = "com.raudonikis.androidskeleton"
        versionCode = 1
        versionName = "1.0"
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
    implementation(project(":libraries:common"))
    implementation(project(":libraries:core"))
    implementation(project(":libraries:common-ui"))
    implementation(project(":libraries:navigation"))
    implementation(project(":libraries:data"))
    implementation(project(":features:bottomnavigation"))
    implementation(project(":features:login"))
    implementation(project(":features:settings"))
    // Hilt
    implementation("com.google.dagger:hilt-android:2.33-beta")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("com.google.dagger:hilt-android-compiler:2.33-beta")
    kapt("androidx.hilt:hilt-compiler:1.0.0-beta01")
    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}