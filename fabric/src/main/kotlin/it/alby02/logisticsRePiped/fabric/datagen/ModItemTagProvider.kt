/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.fabric.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class ModItemTagProvider(
    output: FabricDataOutput,
    completableFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagProvider.ItemTagProvider(output, completableFuture) {

    override fun addTags(arg: HolderLookup.Provider) {
        // Empty for now
    }
}
