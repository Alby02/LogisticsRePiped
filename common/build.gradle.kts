plugins {
    alias(libs.plugins.loom)
    alias(libs.plugins.architectury)
    alias(libs.plugins.kotlin)
}

architectury {
    minecraft = libs.versions.minecraft.get()
    common("fabric", "forge")
}

dependencies {
    "minecraft"(libs.minecraft)
    "mappings"(loom.officialMojangMappings())
    modImplementation(libs.fabric.loader)
    modImplementation(libs.architectury)
}

sourceSets {
    main {
        resources.srcDir("src/main/generated")
    }
}
