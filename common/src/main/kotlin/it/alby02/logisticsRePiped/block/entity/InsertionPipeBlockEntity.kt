/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.block.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class InsertionPipeBlockEntity(pos: BlockPos, state: BlockState) : BasicPipeBlockEntity(pos, state) {
    // Override type once registered
    override fun getType() = ModBlockEntities.INSERTION_PIPE.get()

    fun tick(level: Level, pos: BlockPos, state: BlockState) {
        // Insertion pipes don't strictly need to tick unless they are processing a queue of items
        // trying to enter the adjacent inventory. If the inventory is full, they hold the item.
    }
}
