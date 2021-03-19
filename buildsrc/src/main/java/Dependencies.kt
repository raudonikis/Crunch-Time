object Versions {
    // AndroidX
    const val constraintLayout = "2.0.4"
    const val recyclerView = "1.0.0"
    const val preferences = "1.1.1"
    const val viewBindingKtx = "1.1.1"

    // UI
    const val glide = "4.12.0"
    const val fastAdapter = "5.3.5"

    // Animations
    const val lottie = "3.6.1"

    // Lifecycle
    const val lifecycle = "2.3.0"

    // DI
    const val hilt = "2.32-alpha"
    const val hiltAndroidx = "1.0.0-alpha03"

    // Logging
    const val timber = "4.7.1"

    // Networking
    const val retrofit = "2.9.0"
    const val moshi = "1.11.0"
    const val okHttp = "4.9.0"
    const val networkResponseAdapter = "4.1.0"

    // Room database
    const val room = "2.2.5"

    // Navigation
    const val navigation = "2.3.3"

    // Support
    const val appCompat = "1.2.0"

    // Material
    const val material = "1.3.0"

    // Kotlin
    const val ktxCore = "1.3.2"
    const val kotlin = "1.4.20"
    const val coroutines = "1.4.2"

    // Testing
    const val jUnit = "4.13.1"
    const val jUnitTest = "1.1.2"
    const val espresso = "3.3.0"

    // Sdk
    const val compileSdk = 30
    const val minSdk = 23
    const val targetSdk = 30
    const val buildTools = "30.0.2"

    // Quality
    const val leakCanary = "2.6"
}

object Releases {
    const val applicationId = "com.raudonikis.androidskeleton"
    const val versionCode = 1
    const val versionName = "1.0"
}

object Modules {
    object Libraries {
        const val network = ":libraries:network"
        const val common = ":libraries:common"
        const val commonUi = ":libraries:common-ui"
        const val data = ":libraries:data"
        const val dataDomain = ":libraries:data-domain"
        const val navigation = ":libraries:navigation"
    }

    object Features {
        const val home = ":features:home"
        const val discover = ":features:discover"
        const val profile = ":features:profile"
        const val details = ":features:details"
        const val login = ":features:login"
        const val bottomNavigation = ":features:bottomnavigation"
    }
}

object Dependencies {
    // AndroidX
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val preferences = "androidx.preference:preference:${Versions.preferences}"
    const val viewBindingKtx = "com.github.wada811:ViewBinding-ktx:${Versions.viewBindingKtx}"

    // UI
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideAnnotation = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val fastAdapter = "com.mikepenz:fastadapter:${Versions.fastAdapter}"
    const val fastAdapterViewBinding =
        "com.mikepenz:fastadapter-extensions-binding:${Versions.fastAdapter}"

    // Animations
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"

    // DI
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltLifecycle = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltAndroidx}"
    const val hiltLifecycleCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltAndroidx}"

    // Logging
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // Networking
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okHttp = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    // Networking - converters
    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val scalarsConverter = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"

    // Networking - adapters
    const val networkResponseAdapter =
        "com.github.haroldadmin:NetworkResponseAdapter:${Versions.networkResponseAdapter}"

    // Material
    const val material = "com.google.android.material:material:${Versions.material}"

    // Room database
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomExtensions = "androidx.room:room-ktx:${Versions.room}"

    // Navigation
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    // Support
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"

    // Kotlin
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktxCore}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    // Lifecycle
    const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"


    // Testing
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val jUnitTest = "androidx.test.ext:junit:${Versions.jUnitTest}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    // Quality
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
}