package common

plugins {
    id ("java-library")
    id ("kotlin")
}

configure<JavaPluginConvention>{
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Deps.koinCore)
    implementation(Deps.coroutineCore)

    testImplementation(TestingDeps.coroutine)
    testImplementation(TestingDeps.coroutineCore)
    testImplementation(TestingDeps.junit)
    testImplementation(TestingDeps.truth)
    testImplementation(TestingDeps.mockk)

}