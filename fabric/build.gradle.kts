plugins {
    id("logisticsrepiped.platform-conventions")
    alias(libs.plugins.architectury)
}

architectury {
    minecraft = libs.versions.minecraft.get()
    platformSetupLoomIde()
    fabric()
}

fabricApi {
    configureDataGeneration {
        outputDirectory = file("${project(":common").file("src/main/generated")}")
    }
}

configurations {
    getByName("developmentFabric").extendsFrom(configurations.getByName("common"))
}

dependencies {
    "minecraft"(libs.minecraft)
    "mappings"(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${libs.versions.minecraft.get()}:${libs.versions.parchment.get()}@zip")
    })
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modImplementation(libs.architectury.fabric)
    modImplementation(libs.fabric.kotlin)
}

tasks.processResources {
    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}