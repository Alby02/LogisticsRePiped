plugins {
    id("logisticsrepiped.platform-conventions")
    alias(libs.plugins.architectury)
}

architectury {
    minecraft = libs.versions.minecraft.get()
    platformSetupLoomIde()
    forge()
}

repositories {
    maven("https://thedarkcolour.github.io/KotlinForForge/") {
        name = "Kotlin for Forge"
        content { includeGroup("thedarkcolour") }
    }
}

loom {
    forge {
        mixinConfig("logisticsrepiped.mixins.json")
    }
}

configurations {
    getByName("developmentForge").extendsFrom(configurations.getByName("common"))
}

dependencies {
    "minecraft"(libs.minecraft)
    "mappings"(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${libs.versions.minecraft.get()}:${libs.versions.parchment.get()}@zip")
    })
    "forge"(libs.forge)
    modImplementation(libs.architectury.forge)
    implementation(libs.forge.kotlin)
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("META-INF/mods.toml") {
        expand("version" to project.version)
    }
}