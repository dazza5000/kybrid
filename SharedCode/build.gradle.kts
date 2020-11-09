import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")

    id("com.android.library")
}

kotlin {
    android()

    js {
        browser { }
    }

    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    }

    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    }

    sourceSets["jsMain"].dependencies {
        implementation(kotlin("stdlib-js"))
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    }

    sourceSets["jsTest"].dependencies {
        implementation(kotlin("test-js"))
    }
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(25)
    }
    sourceSets {
        getByName("main") {
            assets.srcDirs(file("src/androidMain/assets"))
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            java.srcDirs(file("src/androidMain/kotlin"))
            res.srcDirs(file("src/androidMain/res"))
        }
    }
}
