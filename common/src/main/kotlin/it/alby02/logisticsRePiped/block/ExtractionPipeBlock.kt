/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.block

import it.alby02.logisticsRePiped.block.entity.ExtractionPipeBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.Container

class ExtractionPipeBlock(properties: Properties) : BasicPipeBlock(properties) {
    
    // Extraction pipes connect to pipes, but they ALSO connect to Inventories
    override fun canConnectTo(state: BlockState, neighborState: BlockState, direction: Direction): Boolean {
        if (super.canConnectTo(state, neighborState, direction)) return true
        
        // This is a simplified check. A true check would require a level context
        // to check for block entities that have capability/inventories at the neighbor position.
        // For placement preview, we'll allow connecting to any block with a BlockEntity.
        // In the true updateShape/tick logic, the BlockEntity handles the actual extraction.
        if (neighborState.hasBlockEntity()) return true
        
        return false
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
        return ExtractionPipeBlockEntity(pos, state)
    }

    override fun <T : BlockEntity> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        if (level.isClientSide) return null
        return BlockEntityTicker { lvl, p, st, be ->
            if (be is ExtractionPipeBlockEntity) {
                be.tick(lvl, p, st)
            }
        }
    }
}
