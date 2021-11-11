import org.gradle.api.artifacts.dsl.DependencyHandler

object BuildPlugins {
    val android by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
    val googleServices by lazy { "com.google.gms:google-services:${Versions.googleServices}" }
}

object Deps {
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val androidxCore by lazy { "androidx.core:core-ktx:${Versions.androidxCore}" }
    val materialDesign by lazy { "com.google.android.material:material:${Versions.materialDesign}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }
    val koinCore by lazy { "io.insert-koin:koin-core:${Versions.koin}" }
    val koinAndroid by lazy { "io.insert-koin:koin-android:${Versions.koin}" }
    val coroutineCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}" }
    val coroutineAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}" }
    val navigationComponent by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}" }
    val navigationUiComponent by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navigation}" }
    val viewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleExtension}" }
    val liveData by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleExtension}" }

    //For DefaultLifeCyleObserver
    val lifeCycleCommon by lazy { "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleExtension}" }

    val firebaseBom by lazy { "com.google.firebase:firebase-bom:${Versions.firebaseBom}" }
    val firebaseAnalytics by lazy { "com.google.firebase:firebase-analytics-ktx" }

    //PinView for OTP
    val otpPinView by lazy { "com.chaos.view:pinview:${Versions.optPinView}" }


}

object TestingDeps {
    val junit by lazy { "junit:junit:${Versions.jUnit}" }
    val junitRunner by lazy { "androidx.test.ext:junit:${Versions.jUnitRunner}" }
    val androidXCore by lazy { "androidx.arch.core:core-testing:${Versions.androidxCoreTesting}" }
    val androidXRules by lazy { "androidx.test:rules:${Versions.androidXRules}" }
    val truth by lazy { "com.google.truth:truth:${Versions.jUnitRunner}" }
    val espresso by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }
    val koinCore by lazy { "io.insert-koin:koin-test:${Versions.koin}" }
    val koinJUnit4 by lazy { "io.insert-koin:koin-test-junit4:${Versions.koin}" }
    val coroutine by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine}" }
    val coroutineCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}" }
    val mockk by lazy { "io.mockk:mockk:${Versions.mockk}" }
    val mockkAndroid by lazy { "io.mockk:mockk-android:${Versions.mockk}" }
    val navigation by lazy { "androidx.navigation:navigation-testing:${Versions.navigation}" }
}





