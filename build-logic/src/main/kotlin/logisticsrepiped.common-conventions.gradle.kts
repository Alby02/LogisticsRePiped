/*
 * Convention plugin for common base configuration.
 * Applied to all subprojects.
 */

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    java
    kotlin("jvm")
}

// Mod properties - shared across all modules
val modVersion = "0.0.1"
val mavenGroup = "it.alby02"
val archivesBaseName = "LogisticsRePiped"

group = mavenGroup
version = modVersion

base {
    archivesName.set("$archivesBaseName-${project.name}")
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

tasks.compileJava.configure {
    options.release.set(17)
}
