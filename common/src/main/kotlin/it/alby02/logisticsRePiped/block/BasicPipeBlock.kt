/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.block

import it.alby02.logisticsRePiped.block.entity.BasicPipeBlockEntity
import it.alby02.logisticsRePiped.block.entity.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

open class BasicPipeBlock(properties: Properties) : BasePipeBlock(properties), EntityBlock {
    


    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
        return BasicPipeBlockEntity(pos, state)
    }

    override fun <T : BlockEntity> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        if (level.isClientSide) return null
        // Ticks aren't strictly necessary for a passive router unless it's actively processing a queue,
        // but we'll provide the outline.
        return null 
    }
}
