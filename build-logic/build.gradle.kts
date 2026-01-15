plugins {
    `kotlin-dsl`
}

dependencies {
    // Plugin dependencies as implementation (marker artifact pattern)
    // These become available to apply in convention plugins
    implementation(libs.loom.plugin)
    implementation(libs.architectury.plugin)
    implementation(libs.shadow.plugin)
    implementation(libs.kotlin.plugin)
}
