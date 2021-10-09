package common


/**
 * Precompiled [android-library-convention.gradle.kts][common.Android_library_convention_gradle] script plugin.
 *
 * @see common.Android_library_convention_gradle
 */
class AndroidLibraryConventionPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("common.Android_library_convention_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
