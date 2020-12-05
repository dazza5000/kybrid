plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
}

kotlin {
    android()

    js{
        browser()
    }

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":Native"))
                implementation("org.jetbrains.kotlin:kotlin-stdlib")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
                implementation("androidx.webkit:webkit:1.3.0")
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(project(":Native"))
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(project(":Kybrid"))
                implementation("org.jetbrains.kotlinx:kotlinx-html:0.7.2")
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
