/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.state.BlockState

class UnroutedPipeBlock(properties: Properties) : BasePipeBlock(properties) {
    
    // Unrouted pipes can ONLY connect to other pipes.
    override fun canConnectTo(state: BlockState, neighborState: BlockState, direction: Direction): Boolean {
        if (!super.canConnectTo(state, neighborState, direction)) return false
        
        // Ensure we don't exceed 2 connections when we format a outgoing connection
        var connections = 0
        for (dir in Direction.entries) {
            if (dir != direction && state.getValue(PROPERTY_BY_DIRECTION[dir]!!)) {
                connections++
            }
        }
        return connections < 2
    }

    override fun acceptsConnectionFrom(state: BlockState, neighborState: BlockState, direction: Direction): Boolean {
        if (!super.acceptsConnectionFrom(state, neighborState, direction)) return false
        
        // Determine number of existing connections
        var connections = 0
        for (dir in Direction.entries) {
            if (state.getValue(PROPERTY_BY_DIRECTION[dir]!!)) {
                connections++
            }
        }
        
        // If we already have a connection on this face, it's just maintaining it.
        if (state.getValue(PROPERTY_BY_DIRECTION[direction]!!)) return true
        
        // Otherwise, only accept if we have less than 2
        return connections < 2
    }

    // Unrouted pipes are restricted to at most 2 connections (wire, corner, dead end, point).
    override fun getStateForPlacement(context: net.minecraft.world.item.context.BlockPlaceContext): BlockState? {
        val defaultState = super.getStateForPlacement(context) ?: return null
        var connections = 0
        var state = defaultState
        for (dir in Direction.entries) {
            val prop = PROPERTY_BY_DIRECTION[dir]!!
            if (state.getValue(prop)) {
                if (connections >= 2) {
                    state = state.setValue(prop, false)
                } else {
                    connections++
                }
            }
        }
        return state
    }
}
