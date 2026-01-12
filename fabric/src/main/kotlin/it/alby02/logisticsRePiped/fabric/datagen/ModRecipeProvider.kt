/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.fabric.datagen

import it.alby02.logisticsRePiped.block.ModBlocks
import it.alby02.logisticsRePiped.item.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.FinishedRecipe
import java.util.function.Consumer

class ModRecipeProvider(output: FabricDataOutput) : FabricRecipeProvider(output) {
    override fun buildRecipes(exporter: Consumer<FinishedRecipe>) {
        // Simple recipe for testing
        // Using correct mapping for compacting recipes roughly:
        // RecipeProvider.nineBlockStorageRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.GREEN_GEM.get(), RecipeCategory.DECORATIONS, ModBlocks.GREEN_GEM_BLOCK.get())
    }
}
