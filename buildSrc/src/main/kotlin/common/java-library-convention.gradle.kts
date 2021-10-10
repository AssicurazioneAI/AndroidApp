package common

plugins {
    id ("java-library")
    id ("kotlin")
}

configure<JavaPluginConvention>{
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation(TestingDeps.junit)
    implementation(Deps.koinCore)
    implementation(Deps.coroutineCore)
}