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