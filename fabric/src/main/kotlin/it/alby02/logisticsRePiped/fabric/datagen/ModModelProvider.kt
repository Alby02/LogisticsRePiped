/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.fabric.datagen

import it.alby02.logisticsRePiped.block.ModBlocks
import it.alby02.logisticsRePiped.item.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelTemplates

class ModModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockModelGenerators) {
        blockStateModelGenerator.createTrivialCube(ModBlocks.GREEN_GEM_BLOCK.get())
        blockStateModelGenerator.createTrivialCube(ModBlocks.GREEN_GEM_ORE.get())
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerators) {
        itemModelGenerator.generateFlatItem(ModItems.GREEN_GEM.get(), ModelTemplates.FLAT_ITEM)
    }
}
