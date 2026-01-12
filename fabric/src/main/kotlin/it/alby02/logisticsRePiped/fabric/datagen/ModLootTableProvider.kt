/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.fabric.datagen

import it.alby02.logisticsRePiped.block.ModBlocks
import it.alby02.logisticsRePiped.item.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.world.level.block.Block
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.world.item.Item
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator

class ModLootTableProvider(dataOutput: FabricDataOutput) : FabricBlockLootTableProvider(dataOutput) {
    override fun generate() {
        dropSelf(ModBlocks.GREEN_GEM_BLOCK.get())

        add(ModBlocks.GREEN_GEM_ORE.get(), copperOreLikeDrops(ModBlocks.GREEN_GEM_ORE.get(), ModItems.GREEN_GEM.get()))
    }

    fun copperOreLikeDrops(silkdrop: Block, normal: Item): LootTable.Builder {
        return createSilkTouchDispatchTable(
            silkdrop,
            applyExplosionDecay(
                silkdrop,
                LootItem.lootTableItem(normal)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 5.0f)))
                    .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
            )
        )
    }
}
