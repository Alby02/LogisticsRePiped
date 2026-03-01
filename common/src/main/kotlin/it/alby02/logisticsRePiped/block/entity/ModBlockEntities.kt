/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.block.entity

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import it.alby02.logisticsRePiped.CommonClass
import it.alby02.logisticsRePiped.block.ModBlocks
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.entity.BlockEntityType

object ModBlockEntities {
    val BLOCK_ENTITIES: DeferredRegister<BlockEntityType<*>> = DeferredRegister.create(CommonClass.MOD_ID, Registries.BLOCK_ENTITY_TYPE)

    val BASIC_PIPE: RegistrySupplier<BlockEntityType<BasicPipeBlockEntity>> = BLOCK_ENTITIES.register("basic_pipe") {
        BlockEntityType.Builder.of(::BasicPipeBlockEntity, ModBlocks.BASIC_PIPE_BLOCK.get()).build(null)
    }

    val EXTRACTION_PIPE: RegistrySupplier<BlockEntityType<ExtractionPipeBlockEntity>> = BLOCK_ENTITIES.register("extraction_pipe") {
        BlockEntityType.Builder.of(::ExtractionPipeBlockEntity, ModBlocks.EXTRACTION_PIPE_BLOCK.get()).build(null)
    }

    val INSERTION_PIPE: RegistrySupplier<BlockEntityType<InsertionPipeBlockEntity>> = BLOCK_ENTITIES.register("insertion_pipe") {
        BlockEntityType.Builder.of(::InsertionPipeBlockEntity, ModBlocks.INSERTION_PIPE_BLOCK.get()).build(null)
    }

    fun register() {
        BLOCK_ENTITIES.register()
    }
}
