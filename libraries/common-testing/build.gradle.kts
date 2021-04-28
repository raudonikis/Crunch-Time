plugins {
    id("com.android.library")
    kotlin("android")
}

dependencies {
    // Projects
    implementation(project(Modules.Libraries.common))
    // Testing
    implementation(Dependencies.jUnit)
    implementation(Dependencies.jUnitTest)
    implementation(Dependencies.mockk)
    implementation(Dependencies.coroutinesTest)
}