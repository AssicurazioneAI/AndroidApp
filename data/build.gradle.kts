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

    buildTypes {
        getByName("release") {
            val RELEASE_BASE_URL: String by project
            buildConfigField("String", "BASE_URL", RELEASE_BASE_URL)

        }

        getByName("debug") {
            val DEV_BASE_URL: String by project
            buildConfigField("String", "BASE_URL", DEV_BASE_URL)
        }


    }
}

dependencies {
    implementation(project(Modules.domain))
    implementRetrofitNetworking()


}

