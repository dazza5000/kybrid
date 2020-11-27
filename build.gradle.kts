// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlin_version by extra("1.4.20")
    repositories {
        google()
        jcenter()

        maven(url="https://plugins.gradle.org/m2/")
    }

    dependencies {
        val kotlinVersion = "1.4.10"

        classpath("com.android.tools.build:gradle:4.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")

        classpath("org.jlleitschuh.gradle:ktlint-gradle:9.4.1")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url="https://jitpack.io")
    }
}
