package common


/**
 * Precompiled [java-library-convention.gradle.kts][common.Java_library_convention_gradle] script plugin.
 *
 * @see common.Java_library_convention_gradle
 */
class JavaLibraryConventionPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("common.Java_library_convention_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
