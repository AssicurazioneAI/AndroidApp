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

    //Animations are disabled for testing purposes
    testOptions {
        animationsDisabled = true
    }

    packagingOptions {

        with(resources.excludes) {
            add("META-INF/DEPENDENCIES")
            add("META-INF/LICENSE")
            add("META-INF/LICENSE.txt")
            add("META-INF/license.txt")
            add("META-INF/NOTICE")
            add("META-INF/notice.txt")
            add("META-INF/NOTICE")
            add("META-INF/NOTICE.txt")
            add("META-INF/LGPL2.1")
            add("META-INF/ASL2.0")
            add("META-INF/AL2.0")
            add("META-INF/*.kotlin_module")
        }


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
    androidTestImplementation(TestingDeps.truth)
    androidTestImplementation(TestingDeps.mockkAndroid)
    androidTestImplementation(TestingDeps.coroutine)


}