plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")

    id("com.android.library")
}

kotlin {
    android()

    js {
        browser()
    }

    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    }

    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
        implementation("androidx.webkit:webkit:1.3.0")
    }

    sourceSets["jsMain"].dependencies {
        implementation(kotlin("stdlib-js"))
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    }
}

android {
    compileSdkVersion(30)
    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(30)
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
