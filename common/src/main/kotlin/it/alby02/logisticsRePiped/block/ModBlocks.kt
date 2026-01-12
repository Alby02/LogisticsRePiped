/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.block

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import it.alby02.logisticsRePiped.CommonClass
import it.alby02.logisticsRePiped.item.ModItemGroups
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.core.registries.Registries

object ModBlocks {
    val BLOCKS: DeferredRegister<Block> = DeferredRegister.create(CommonClass.MOD_ID, Registries.BLOCK)
    val ITEMS: DeferredRegister<Item> = DeferredRegister.create(CommonClass.MOD_ID, Registries.ITEM)

    val GREEN_GEM_BLOCK: RegistrySupplier<Block> = registerBlock("greengemblock",
        Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST_CLUSTER))
    )
    
    val GREEN_GEM_ORE: RegistrySupplier<Block> = registerBlock("greengemore",
        Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST_CLUSTER))
    )

    private fun registerBlock(name: String, block: Block): RegistrySupplier<Block> {
        val registeredBlock = BLOCKS.register(name) { block }
        ITEMS.register(name) {
             BlockItem(registeredBlock.get(), Item.Properties().`arch$tab`(ModItemGroups.GREEN_GEM_GROUP))
        }
        return registeredBlock
    }

    fun register() {
        BLOCKS.register()
        ITEMS.register()
    }
}
