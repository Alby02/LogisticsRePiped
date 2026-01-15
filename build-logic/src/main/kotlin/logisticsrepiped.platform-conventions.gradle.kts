/*
 * Convention plugin for platform modules (Fabric/Forge).
 * Extends loom-conventions with shadow JAR configuration.
 */

plugins {
    id("logisticsrepiped.common-conventions")
    id("com.gradleup.shadow")
    id("dev.architectury.loom")
}

// Configuration for common project dependency
val common by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

// Configuration for shadow bundling
val shadowBundle by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

configurations {
    compileClasspath.get().extendsFrom(common)
    runtimeClasspath.get().extendsFrom(common)
}

dependencies {
    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowBundle(project(path = ":common", configuration = "transformProductionForge"))
}

tasks.shadowJar {
    configurations = listOf(shadowBundle)
    archiveClassifier.set("dev-shadow")
}

tasks.remapJar {
    input.set(tasks.shadowJar.get().archiveFile)
}
