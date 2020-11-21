plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
}

kotlin {
    android()

    ios()

    js {
        browser()
    }

    val serializationVersion = "1.0.1"


    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
                implementation("androidx.webkit:webkit:1.3.0")
            }
        }

        val iosMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
            }
        }
    }

}

android {
    compileSdkVersion(Versions.compile_sdk)
    defaultConfig {
        minSdkVersion(Versions.min_sdk)
        targetSdkVersion(Versions.target_sdk)
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
