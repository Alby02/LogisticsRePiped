plugins {
    id("logisticsrepiped.common-conventions")
    alias(libs.plugins.loom)
    alias(libs.plugins.architectury)
}

architectury {
    minecraft = libs.versions.minecraft.get()
    common("fabric", "forge")
}

loom {
    mixin {
        useLegacyMixinAp = true
        defaultRefmapName.set("logisticsrepiped.refmap.json")
    }
}

dependencies {
    "minecraft"(libs.minecraft)
    "mappings"(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${libs.versions.minecraft.get()}:${libs.versions.parchment.get()}@zip")
    })
    modImplementation(libs.fabric.loader)
    modImplementation(libs.architectury)
}

sourceSets {
    main {
        resources.srcDir("src/main/generated")
    }
}
