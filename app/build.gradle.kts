plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")

}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = (ConfigData.appId)
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(project(Modules.domain))
    implementation(project(Modules.data))

    implementation(Deps.androidxCore)
    implementation(Deps.appCompat)
    implementation(Deps.materialDesign)
    implementation(Deps.constraintLayout)
    implementation(Deps.timber)
    implementation(Deps.viewModel)
    implementation(Deps.liveData)
    implementCoroutine()
    implementNavigationComponent()
    implementKoinAndroid()

    testImplementation(TestingDeps.junit)
    androidTestImplementation(TestingDeps.junitRunner)
    androidTestImplementation(TestingDeps.espresso)
}