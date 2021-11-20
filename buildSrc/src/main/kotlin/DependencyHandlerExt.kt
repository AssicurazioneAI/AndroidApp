import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementCoroutine() {
    implement(Deps.coroutineCore)
    implement(Deps.coroutineAndroid)
}

fun DependencyHandler.implementNavigationComponent() {
    implement(Deps.navigationComponent)
    implement(Deps.navigationUiComponent)
}

fun DependencyHandler.implementKoinAndroid() {
    implement(Deps.koinCore)
    implement(Deps.koinAndroid)
}

fun DependencyHandler.implementCameraX() {
    implement(Deps.cameraXCore)
    implement(Deps.cameraXLifeCycle)
    implement(Deps.cameraXView)
}

fun DependencyHandler.implementRetrofitNetworking() {
    implement(Deps.moshi)
    implement(Deps.moshiKotlin)
    implement(Deps.retrofit2)
    implement(Deps.okHttpLogger)
    implement(Deps.retrofit2Converter)
}

fun DependencyHandler.testImplementKoin() {
    testImplement(TestingDeps.koinCore)
    testImplement(TestingDeps.koinJUnit4)
}

fun DependencyHandler.implement(dependency: Any) {
    add("implementation", dependency)
}

fun DependencyHandler.testImplement(dependency: Any) {
    add("testImplementation", dependency)
}