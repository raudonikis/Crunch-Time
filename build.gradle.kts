import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.android.build.gradle.BaseExtension

buildscript {
    val kotlin_version by extra("1.4.20")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31")
        // Hilt
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.33-beta")
        // Navigation
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.3")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
    }
}

subprojects {
    tasks.withType<KotlinCompile>() {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
    afterEvaluate {
        if (hasProperty("android")) {
            extensions.configure(BaseExtension::class.java) {
                applyAndroidCommons()
            }
        }
    }
}

fun BaseExtension.applyAndroidCommons() {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(30)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    lintOptions {
        isAbortOnError = false
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.register("runOnGitHub") {
    dependsOn(tasks.withType(Test::class.java))
    group = "custom"
    description = "$ ./gradlew runOnGitHub # runs on GitHub Action"
}