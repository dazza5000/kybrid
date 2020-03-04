import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm("android")

    js {
        browser { }
    }

    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
    }

    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
    }

    sourceSets["jsMain"].dependencies {
            implementation(kotlin("stdlib-js"))

    }

    sourceSets["jsTest"].dependencies {
        implementation(kotlin("test-js"))
    }
}