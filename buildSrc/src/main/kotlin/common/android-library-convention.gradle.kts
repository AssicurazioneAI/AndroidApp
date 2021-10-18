package common

import gradle.kotlin.dsl.accessors._c7bb97a9059e4eaba8014dd04814a02c.implementation
import gradle.kotlin.dsl.accessors._c7bb97a9059e4eaba8014dd04814a02c.testImplementation

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {
    implementation(Deps.androidxCore)
    implementation(Deps.appCompat)
    implementation(Deps.materialDesign)
    implementation(Deps.constraintLayout)
    implementation(Deps.timber)
    implementation(Deps.koinCore)
    implementation(Deps.coroutineCore)

    testImplementation(TestingDeps.junit)
    testImplementation(TestingDeps.coroutineCore)
    testImplementation(TestingDeps.truth)
    testImplementation(TestingDeps.mockk)
    testImplementation(TestingDeps.coroutine)

    androidTestImplementation(TestingDeps.junitRunner)
    androidTestImplementation(TestingDeps.espresso)
}