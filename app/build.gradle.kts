plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
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
    implementation(platform(Deps.firebaseBom))
    implementation(platform(Deps.firebaseAnalytics))

    implementCoroutine()
    implementNavigationComponent()
    implementKoinAndroid()


    testImplementation(TestingDeps.junit)
    testImplementation(TestingDeps.mockk)
    testImplementation(TestingDeps.truth)
    testImplementation(TestingDeps.androidXCore)
    testImplementation(TestingDeps.coroutine)
    androidTestImplementation(TestingDeps.junitRunner)
    androidTestImplementation(TestingDeps.espresso)
}