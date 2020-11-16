plugins {
    id("org.jetbrains.kotlin.js")
    kotlin("plugin.serialization")
}

kotlin {
    js {
        browser()
    }

    sourceSets["main"].dependencies {
        implementation(kotlin("stdlib-js"))
        implementation(project(":Native"))
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    }
}
