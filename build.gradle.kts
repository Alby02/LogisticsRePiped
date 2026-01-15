plugins {
    alias(libs.plugins.architectury)
    alias(libs.plugins.loom) apply false
}

architectury {
    minecraft = libs.versions.minecraft.get()
}
