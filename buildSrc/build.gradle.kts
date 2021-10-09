import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins{
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

object PluginsVersions {
    const val gradlePlugin="7.0.2"
    const val kotlin="1.5.21"
}

dependencies {
    // android gradle plugin, required by custom plugin
    implementation("com.android.tools.build:gradle:${PluginsVersions.gradlePlugin}")

    // kotlin plugin, required by custom plugin
    implementation(kotlin("gradle-plugin", PluginsVersions.kotlin))
}


