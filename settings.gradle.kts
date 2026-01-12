pluginManagement {
    repositories {
        maven(url = "https://maven.fabricmc.net/")
        maven(url = "https://maven.architectury.dev/")
        maven(url = "https://files.minecraftforge.net/maven/")
        gradlePluginPortal()
    }
}

rootProject.name = "LogisticsRePiped"

include("common")
include("fabric")
include("forge")
