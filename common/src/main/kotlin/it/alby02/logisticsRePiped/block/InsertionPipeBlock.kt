/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.block

import it.alby02.logisticsRePiped.block.entity.InsertionPipeBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class InsertionPipeBlock(properties: Properties) : BasicPipeBlock(properties) {
    
    // Insertion pipes connect to pipes, but they ALSO connect to Inventories
    override fun canConnectTo(state: BlockState, neighborState: BlockState, direction: Direction): Boolean {
        if (super.canConnectTo(state, neighborState, direction)) return true
        
        // Similarly to extraction pipes, we allow visual connection to blocks with block entities.
        if (neighborState.hasBlockEntity()) return true
        
        return false
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
        return InsertionPipeBlockEntity(pos, state)
    }

    override fun <T : BlockEntity> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        if (level.isClientSide) return null
        return BlockEntityTicker { lvl, p, st, be ->
            if (be is InsertionPipeBlockEntity) {
                be.tick(lvl, p, st)
            }
        }
    }
}
