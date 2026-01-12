import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.loom) apply false
    alias(libs.plugins.architectury)
    alias(libs.plugins.shadow) apply false
    alias(libs.plugins.kotlin) apply false
}

val mod_version = "0.0.1"
val maven_group = "it.alby02"
val archives_name = "LogisticsRePiped"

architectury {
    minecraft = libs.versions.minecraft.get()
}

allprojects {
    group = maven_group
    version = mod_version
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    extensions.configure<BasePluginExtension> {
        archivesName.set("$archives_name-${project.name}")
    }

    extensions.configure<JavaPluginExtension> {
        withSourcesJar()
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<KotlinCompile> {
        compilerOptions{
            jvmTarget = JvmTarget.JVM_17
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.release.set(17)
    }

    repositories {
        // Add repositories to retrieve artifacts from in here.
    }
    
    // Make properties accessible for resource expansion in modules
    extra["mod_version"] = mod_version
    extra["maven_group"] = maven_group
    extra["archives_name"] = archives_name
}

