object BuildPlugins{
    val android by lazy {"com.android.tools.build:gradle:${Versions.gradlePlugin}"}
    val kotlin by lazy {"org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"}
}

object Deps {
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val androidxCore by lazy { "androidx.core:core-ktx:${Versions.androidxCore}" }
    val materialDesign by lazy { "com.google.android.material:material:${Versions.materialDesign}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }
}

object TestingDeps{
    val junit by lazy { "junit:junit:${Versions.jUnit}" }
    val junitRunner by lazy { "androidx.test.ext:junit:${Versions.jUnitRunner}" }
    val espresso by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }
}

