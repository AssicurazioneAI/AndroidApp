plugins {
    id("common.android-library-convention")
}

android{
    sourceSets {
        getByName("test"){
            java.srcDirs("src/test/java")
            java.srcDir(project(Modules.domain).file("src/test/java"))
        }
    }
}

dependencies {
    implementation(project(Modules.domain))

}

